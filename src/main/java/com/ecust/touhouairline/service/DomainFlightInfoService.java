package com.ecust.touhouairline.service;

import com.ecust.touhouairline.consts.DomainFlightInfoConsts;
import com.ecust.touhouairline.consts.FlightConsts;
import com.ecust.touhouairline.entity.FlightEntity;
import com.ecust.touhouairline.entity.OrderMasterEntity;
import com.ecust.touhouairline.entity.PlaneEntity;
import com.ecust.touhouairline.repository.FlightRepository;
import com.ecust.touhouairline.repository.PlaneRepository;
import com.ecust.touhouairline.utils.Result;
import com.ecust.touhouairline.utils.SingleMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 姚迟亮
 * 创建日期：2019-6-19
 **/
@Service
public class DomainFlightInfoService {
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    PlaneRepository planeRepository;
    @Autowired
    DomainOrderService domainOrderService;

    public SingleMessageResult addFlight(FlightEntity flight){
        SingleMessageResult checkResult = checkFlight(flight);
        if (!checkResult.isSuccess()) return checkResult;
        if (flightRepository.existsById(flight.getFlightNo()))
            return new SingleMessageResult(false,DomainFlightInfoConsts.FLIGHT_EXISTS_ERROR);
        flight.setFlightState(FlightConsts.PREPARING);
        PlaneEntity plane = planeRepository.findByPlaneNo(flight.getPlaneNo());
        flight.setPlaneByPlaneNo(plane);
        flightRepository.save(flight);
        return new SingleMessageResult(true,DomainFlightInfoConsts.FLIGHT_CREATE_SUCCESS);
    }

    public SingleMessageResult changeFlight(FlightEntity check){
        SingleMessageResult checkResult = checkFlight(check);
        if (!checkResult.isSuccess()) return checkResult;
        check.setFlightState(FlightConsts.PREPARING);
        PlaneEntity plane = planeRepository.findByPlaneNo(check.getPlaneNo());
        check.setPlaneByPlaneNo(plane);
        if (!check.getFlightState().equals(FlightConsts.ARRIVED) && !check.getFlightState().equals(FlightConsts.CANCELED))
            cancelOrdersByFlightNo(check.getFlightNo());
        flightRepository.save(check);
        return new SingleMessageResult(true,DomainFlightInfoConsts.FLIGHT_CHANGE_SUCCESS);
    }

    public SingleMessageResult deleteFlight(String flightNo){
        if (!flightRepository.existsById(flightNo)) return new SingleMessageResult(false,DomainFlightInfoConsts.UNKNOWN_ERROR);
        FlightEntity flight = flightRepository.getOne(flightNo);
        return deleteFlight(flight);
    }

    public SingleMessageResult deleteFlight(FlightEntity flight){
        if (!flightRepository.existsById(flight.getPlaneNo())) return new SingleMessageResult(false,DomainFlightInfoConsts.UNKNOWN_ERROR);
        if (!flight.getFlightState().equals(FlightConsts.ARRIVED) && !flight.getFlightState().equals(FlightConsts.CANCELED))
            cancelOrdersByFlightNo(flight.getFlightNo());
        flightRepository.delete(flight);
        return new SingleMessageResult(true, DomainFlightInfoConsts.FLIGHT_DELETE_SUCCESS);
    }

    public SingleMessageResult changeFlightState(String flightNo,String state){
        if (!flightRepository.existsById(flightNo)) return new SingleMessageResult(false,DomainFlightInfoConsts.UNKNOWN_ERROR);
        FlightEntity flight = flightRepository.getOne(flightNo);
        return changeFlightState(flight,state);
    }

    public SingleMessageResult changeFlightState(FlightEntity flight,String state){
        if (flight.getFlightState().equals(state)) return new SingleMessageResult(false,DomainFlightInfoConsts.FLIGHT_STATUS_SAME);
        if (state.equals(FlightConsts.CANCELED)) return new SingleMessageResult(false,DomainFlightInfoConsts.FLIGHT_STATUS_CANCEL);
        flight.setFlightState(state);
        flightRepository.saveAndFlush(flight);
        return new SingleMessageResult(true,DomainFlightInfoConsts.FLIGHT_STATUS_SUCCESS);
    }

    private void cancelOrdersByFlightNo(String flightNo){
        Result<Collection<OrderMasterEntity>> result = domainOrderService.getOrderMasterByFlight(flightNo);
        if (result.isSuccess()) {
            result.getObject().forEach(u -> domainOrderService.cancelOrder(u,true));
        }
    }

    private SingleMessageResult checkFlight(FlightEntity flight){
        if (flight.getFlightNo().isEmpty())
            return new SingleMessageResult(false,DomainFlightInfoConsts.FLIGHT_ERROR);
        if (flight.getDepartPlace().isEmpty())
            return new SingleMessageResult(false,DomainFlightInfoConsts.DEPART_PLACE_ERROR);
        if (flight.getDestination().isEmpty())
            return new SingleMessageResult(false,DomainFlightInfoConsts.DESTINATION_ERROR);
        if (flight.getDepartTime().equals(0))
            return new SingleMessageResult(false,DomainFlightInfoConsts.DEPART_TIME_ERROR);
        if (flight.getArrivedTime().equals(0))
            return new SingleMessageResult(false,DomainFlightInfoConsts.ARRIVED_TIME_ERROR);
        if (flight.getEconomyPrice() == 0)
            return new SingleMessageResult(false,DomainFlightInfoConsts.ECONOMY_PRICE_ERROR);
        if (flight.getPremiumPrice() == 0)
            return new SingleMessageResult(false,DomainFlightInfoConsts.PREMIUM_PRICE_ERROR);
        if (flight.getFirstPrice() == 0)
            return new SingleMessageResult(false,DomainFlightInfoConsts.FIRST_PRICE_ERROR);
        if (flight.getPlaneNo().isEmpty() || !planeRepository.existsById(flight.getPlaneNo()))
            return new SingleMessageResult(false,DomainFlightInfoConsts.PLANE_ERROR);
        return new SingleMessageResult(true,null);
    }
}
