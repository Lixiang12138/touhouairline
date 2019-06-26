package com.ecust.touhouairline.service;

import com.ecust.touhouairline.consts.DomainOrderConsts;
import com.ecust.touhouairline.consts.OrderDetailConsts;
import com.ecust.touhouairline.consts.OrderMasterConsts;
import com.ecust.touhouairline.entity.FlightEntity;
import com.ecust.touhouairline.entity.OrderDetailEntity;
import com.ecust.touhouairline.entity.OrderMasterEntity;
import com.ecust.touhouairline.entity.UserEntity;
import com.ecust.touhouairline.repository.FlightRepository;
import com.ecust.touhouairline.repository.OrderDetailRepository;
import com.ecust.touhouairline.repository.OrderMasterRepository;
import com.ecust.touhouairline.repository.UserRepository;
import com.ecust.touhouairline.utils.Result;
import com.ecust.touhouairline.utils.ResultWithSingleMessage;
import com.ecust.touhouairline.utils.SingleMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dom.DOMCryptoContext;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author 姚迟亮
 * 创建日期：2019-6-19
 **/
@Service
public class DomainOrderService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FlightRepository flightRepository;
    /**
     * 显示订单详情
     * @param orderNo 订单id
     * @return 订单详情的集合
     */
    public Result<Collection<OrderDetailEntity>> showOrderDetail(Integer orderNo){
        if (!orderMasterRepository.existsById(orderNo)) return new Result<>(false,null);
        OrderMasterEntity orderMaster = orderMasterRepository.getOne(orderNo);
        return showOrderDetail(orderMaster);
    }

    /**
     * 显示订单详情
     * @param orderMaster 订单
     * @return 订单详情的集合
     */
    public Result<Collection<OrderDetailEntity>> showOrderDetail(OrderMasterEntity orderMaster){
        return new Result<>(true,orderMaster.getOrderdetailsByOrderNo());
    }

    /**
     *
     * @param orderMaster 订单，应当包含下单用户、航班、舱位
     * @param orderDetails 订单详情，应当包含乘客的信息，座位号可空
     * @return 一条消息 是否成功
     */
    public SingleMessageResult createOrder(OrderMasterEntity orderMaster, Collection<OrderDetailEntity> orderDetails){
        orderMaster.setState(OrderMasterConsts.UNPAID);
        orderMaster.setOrderDate(new Date(new java.util.Date().getTime()));
        int sum = 0;
        String ticketClass = orderMaster.getTicketClass();
        int singleTicketPrice = 0;
        if(ticketClass.equals(OrderMasterConsts.ECONOMY_CLASS)){
            singleTicketPrice = orderMaster.getFlightByFlightNo().getEconomyPrice();
        }
        else if(ticketClass.equals(OrderMasterConsts.PREMIUM_CLASS)){
            singleTicketPrice = orderMaster.getFlightByFlightNo().getPremiumPrice();
        }
        else if(ticketClass.equals(OrderMasterConsts.FIRST_CLASS)){
            singleTicketPrice = orderMaster.getFlightByFlightNo().getFirstPrice();
        }
        else return new SingleMessageResult(false,DomainOrderConsts.MONEY_NOT_MATCH_ERROR);
        for(OrderDetailEntity orderDetail : orderDetails){
            orderDetail.setState(OrderDetailConsts.BEFORE_CHECK_IN);
            if(orderDetail.getPassengerType().equals(OrderDetailConsts.ADULT)) {
                sum += singleTicketPrice;
                orderDetail.setFee(singleTicketPrice);
            }
            else{
                sum += singleTicketPrice / 2;
                orderDetail.setFee(singleTicketPrice / 2);
            }
            orderDetail.setOrdermasterByOrderNo(orderMaster);
        }
        orderMaster.setOrderdetailsByOrderNo(orderDetails);
        orderMasterRepository.save(orderMaster);
        orderDetailRepository.saveAll(orderDetails);
        if(sum != orderMaster.getSum())
            return new SingleMessageResult(false,DomainOrderConsts.MONEY_NOT_MATCH_ERROR);
        return new SingleMessageResult(true, DomainOrderConsts.CREATE_SUCCESS);
    }

    /**
     *
     * @param orderNo 订单id
     * @return 订单退款金额和积分
     */
    public ResultWithSingleMessage<Collection<String>> cancelOrder(Integer orderNo){
        if (!orderMasterRepository.existsById(orderNo)) return new ResultWithSingleMessage<>(false,null,DomainOrderConsts.ERROR);
        OrderMasterEntity orderMaster = orderMasterRepository.getOne(orderNo);
        return cancelOrder(orderMaster,false);
    }

    /**
     *
     * @param orderMaster 订单实体
     * @return 订单退款金额和积分
     */
    public ResultWithSingleMessage<Collection<String>> cancelOrder(OrderMasterEntity orderMaster,boolean bySystem){
        //取消订单应退钱款和积分
        int returnMoney = orderMaster.getSum();
        int returnCredit = orderMaster.getUsedCredit();
        String state = orderMaster.getState();
        java.util.Date today = new java.util.Date();
        //如果是未付款订单，可以随时取消。
        if(state.equals(OrderMasterConsts.UNPAID)){
            orderMaster.setState(OrderMasterConsts.CANCELLED);
            orderMasterRepository.save(orderMaster);
            return new ResultWithSingleMessage<>(true,
                    Arrays.asList(Integer.toString(returnCredit),Integer.toString(returnMoney)),
                    DomainOrderConsts.CANCEL_SUCCESS);
        }
        else if(state.equals(OrderMasterConsts.PAID)){
            orderMaster.setState(OrderMasterConsts.CANCELLED);
            orderMasterRepository.save(orderMaster);
            //取消订单操作距离飞机起飞还有days天
            long days = (orderMaster.getFlightByFlightNo().getDepartTime().getTime() - today.getTime()) / 1000 / 60 / 60 / 24;
            if(days <= 15 && !bySystem){
                //如果距离起飞半个月内取消订单收取50%手续费
                returnMoney /= 2;
            }
            //退还积分
            UserEntity user = orderMaster.getUserByUserNo();
            user.setCredit(user.getCredit() + returnCredit);
            userRepository.save(user);
            return new ResultWithSingleMessage<>(true,
                    Arrays.asList(Integer.toString(returnCredit),Integer.toString(returnMoney)),
                    DomainOrderConsts.CANCEL_SUCCESS);
        }
        else{
            return new ResultWithSingleMessage<>(false,null, DomainOrderConsts.ERROR);
        }
    }

    /**
     *
     * @param orderNo 接受需要付款的订单id
     * @return 如果成功返回支付的金额和积分，否则返回失败信息
     */
    public ResultWithSingleMessage<Collection<String>> payOrder(Integer orderNo){
        if (!orderMasterRepository.existsById(orderNo)) return new ResultWithSingleMessage<>(false,null,DomainOrderConsts.ERROR);
        OrderMasterEntity orderMaster = orderMasterRepository.getOne(orderNo);
        return payOrder(orderMaster);
    }

    /**
     *
     * @param orderMaster 接受需要付款的订单
     * @return 如果成功返回支付的金额和积分，否则返回失败信息
     */
    public ResultWithSingleMessage<Collection<String>> payOrder(OrderMasterEntity orderMaster){
        if(orderMaster.getState().equals(OrderMasterConsts.UNPAID)){
            //积分不够 应当转移至创建订单
            /*if(creditsUsed > orderMaster.getUserByUserNo().getCredit()){
                return new ResultWithSingleMessage<>(false,null,DomainOrderConsts.CREDIT_NOT_ENOUGH);
            }*/
            ResultWithSingleMessage<Collection<String>> ret = new ResultWithSingleMessage<>(true,
                    Arrays.asList(Integer.toString(orderMaster.getSum()),Integer.toString(orderMaster.getUsedCredit())),
                    DomainOrderConsts.PAY_SUCCESS);
            orderMaster.setState(OrderMasterConsts.PAID);
            orderMasterRepository.saveAndFlush(orderMaster);
            return ret;
        }
        else {
            return new ResultWithSingleMessage<>(false,null,DomainOrderConsts.ERROR);
        }
    }

    /**
     * 通过航班获取所有包括该航班的订单
     * @param flightNo 航班号
     * @return 订单集合
     */
    public Result<Collection<OrderMasterEntity>> getOrderMasterByFlight(String flightNo){
        FlightEntity flightEntity = flightRepository.getOne(flightNo);
        Collection<OrderMasterEntity> orderMasterEntities = orderMasterRepository.findAllByFlightByFlightNoAndStateIsNotIn(
                flightEntity,
                Arrays.asList(OrderMasterConsts.CANCELLED,OrderMasterConsts.CHANGED,OrderMasterConsts.FINISHED)
        );
        return new Result<>(!orderMasterEntities.isEmpty(),orderMasterEntities);
    }
}
