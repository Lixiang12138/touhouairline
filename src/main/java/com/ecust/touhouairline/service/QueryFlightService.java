package com.ecust.touhouairline.service;

import com.ecust.touhouairline.consts.OrderMasterConsts;
import com.ecust.touhouairline.entity.FlightEntity;
import com.ecust.touhouairline.entity.OrderDetailEntity;
import com.ecust.touhouairline.entity.OrderMasterEntity;
import com.ecust.touhouairline.entity.PlaneEntity;
import com.ecust.touhouairline.repository.FlightRepository;
import com.ecust.touhouairline.repository.OrderDetailRepository;
import com.ecust.touhouairline.repository.OrderMasterRepository;
import com.ecust.touhouairline.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author 姚迟亮
 * 创建日期：2019-6-19
 **/
@Service
public class QueryFlightService {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    /**
     * 查询单程机票
     * @param departTime 出发时间
     * @param departPlace 出发地点
     * @param destination 到达地点
     * @param passengerNum 乘客数量
     * @return 返回出发当天的航班列表
     */
    public Result<Collection<FlightEntity>> queryOneWayTicket(
            Timestamp departTime, String departPlace, String destination, int passengerNum){
        //只查询一天内的机票
        Timestamp end = (Timestamp)departTime.clone();
        end.setHours(23);
        end.setMinutes(59);
        end.setSeconds(59);
        Collection<FlightEntity> flights =
                flightRepository.findAllByDepartTimeBetweenAndDepartPlaceEqualsAndDestinationEquals(
                        departTime,end,departPlace,destination);
        return new Result<>(!flights.isEmpty(),flights);
    }

    /**
     * 查询来回机票
     * @param departTime 出发时间
     * @param returnTime 回程时间
     * @param departPlace 出发地点
     * @param destination 到达地点
     * @param isOneWay 是否单程
     * @return 返回出发当天和回程当天的航班列表
     */
    public Result<Map<String,Collection<FlightEntity>>> queryTwoWayTicket(
            Timestamp departTime,Timestamp returnTime, String departPlace, String destination, boolean isOneWay){
        //只查询一天内的机票
        Timestamp end = (Timestamp)departTime.clone();
        end.setHours(23);
        end.setMinutes(59);
        end.setSeconds(59);
        Collection<FlightEntity> flights =
                flightRepository.findAllByDepartTimeBetweenAndDepartPlaceEqualsAndDestinationEquals(
                        departTime,end,departPlace,destination);
        Map<String,Collection<FlightEntity>> map = new LinkedHashMap();
        if (!flights.isEmpty()) map.put("depart",flights);
        if (!isOneWay){
            end = (Timestamp)returnTime.clone();
            end.setHours(23);
            end.setMinutes(59);
            end.setSeconds(59);
            Collection<FlightEntity> flightsReturn =
                    flightRepository.findAllByDepartTimeBetweenAndDepartPlaceEqualsAndDestinationEquals(
                            returnTime,end,destination,departPlace);
            if (!flightsReturn.isEmpty()) map.put("return",flightsReturn);
        }
        return new Result<>(map.size() == (isOneWay ? 1 : 2),map);
    }

    /**
     * 显示航班详情
     * @param flightNo 航班号
     * @return 三种舱位的位置数量
     */
    public Result<Map<String,Integer>> showDetailByFlight(String flightNo){
        if(!flightRepository.existsById(flightNo)) return new Result<>(false,null);
        FlightEntity flight = flightRepository.getOne(flightNo);
        return showDetailByFlight(flight);
    }

    /**
     * 显示航班详情
     * @param flight 航班实体
     * @return 三种舱位的位置数量
     */
    public Result<Map<String,Integer>> showDetailByFlight(FlightEntity flight){
        PlaneEntity plane = flight.getPlaneByPlaneNo();
        //获取三种舱位的最大数量
        int economy = plane.getEconomyClass();
        int premium = plane.getPremiumClass();
        int first = plane.getFirstClass();
        List<OrderMasterEntity> orderMasters = orderMasterRepository.findAllByFlightByFlightNo(flight);
        //减去已经预定好的位置
        for(OrderMasterEntity orderMaster : orderMasters){
            if(orderMaster.getTicketClass().equals(OrderMasterConsts.ECONOMY_CLASS)){
                economy -= orderMaster.getOrderdetailsByOrderNo().size();
            }
            else if(orderMaster.getTicketClass().equals(OrderMasterConsts.PREMIUM_CLASS)){
                premium -= orderMaster.getOrderdetailsByOrderNo().size();
            }
            else{
                first -= orderMaster.getOrderdetailsByOrderNo().size();
            }
        }
        Map<String,Integer> map = new HashMap<>();
        map.put(OrderMasterConsts.ECONOMY_CLASS,economy);
        map.put(OrderMasterConsts.PREMIUM_CLASS,premium);
        map.put(OrderMasterConsts.FIRST_CLASS,first);
        return new Result<>(true,map);
    }
}
