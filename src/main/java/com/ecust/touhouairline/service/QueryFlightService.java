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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return
     */
    public Result<Collection<FlightEntity>> queryOneWayTicket(
            Timestamp departTime, String departPlace, String destination, int passengerNum){
        //只查询一天内的机票
        Timestamp end = (Timestamp)departTime.clone();
        end.setHours(23);
        end.setMinutes(59);
        end.setSeconds(59);
        Collection<FlightEntity> flights =
                flightRepository.findFlightEntitiesByDepartTimeBetweenAndDepartPlaceEqualsAndDestinationEquals(
                        departTime,end,departPlace,destination);
        return new Result<>(!flights.isEmpty(),flights);
    }

    public Result<Map<String,Integer>> showDetailByFlight(FlightEntity flight){
        PlaneEntity plane = flight.getPlaneByPlaneNo();
        int economy = plane.getEconomyClass();
        int premium = plane.getPremiumClass();
        int first = plane.getFirstClass();
        List<OrderMasterEntity> orderMasters = orderMasterRepository.findAllByFlightByFlightNo(flight);
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
        System.out.println(economy);

        return new Result<>(true,map);
    }
}
