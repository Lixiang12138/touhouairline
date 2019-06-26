package com.ecust.touhouairline.service;

import com.ecust.touhouairline.consts.DomainUserInfoConsts;
import com.ecust.touhouairline.entity.PassengerEntity;
import com.ecust.touhouairline.entity.UserEntity;
import com.ecust.touhouairline.repository.UserRepository;
import com.ecust.touhouairline.utils.MultiMessageResult;
import com.ecust.touhouairline.utils.ResultWithSingleMessage;
import com.ecust.touhouairline.utils.SingleMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 李霄
 * 创建日期：2019-6-19
 **/
@Service
public class DomainUserInfoService {
    @Autowired
    UserRepository userRepository;

    public SingleMessageResult changeUserInfo(UserEntity user, UserEntity result){
        if (!result.getPassword().isEmpty()) {
            if (result.getPassword().length() < 6 || result.getPassword().length() > 33) new SingleMessageResult(false,DomainUserInfoConsts.USER_CHANGE_SUCCESS);
            user.setPassword(result.getPassword());
        }
        if (!result.getNickName().isEmpty()) user.setNickName(result.getNickName());
        if (!result.getEmail().isEmpty()) user.setEmail(result.getEmail());
        if (!result.getUserPhone().isEmpty()) user.setUserPhone(result.getUserPhone());
        userRepository.save(user);
        return new SingleMessageResult(true,DomainUserInfoConsts.USER_CHANGE_SUCCESS);
    }

    public ResultWithSingleMessage<Collection<PassengerEntity>> showPassages(UserEntity user){
        if(user.getPassengersByUserNo().isEmpty()) return new ResultWithSingleMessage(false,null,DomainUserInfoConsts.PASSENGER_NOT_FOUND);
        return new ResultWithSingleMessage(true,user.getPassengersByUserNo(),null);
    }

    public MultiMessageResult addPassenger(UserEntity user, PassengerEntity passenger){
        Map<String,String> stringMap = new LinkedHashMap();
        if (passenger.getPassengerName().isEmpty())
            stringMap.put("passengerNameError",DomainUserInfoConsts.PASSENGER_NAME_ERROR);
        if (passenger.getPassengerType().isEmpty())
            stringMap.put("passengerTypeError",DomainUserInfoConsts.PASSENGER_TYPE_ERROR);
        if (passenger.getSex().isEmpty())
            stringMap.put("passengerSexError",DomainUserInfoConsts.PASSENGER_SEX_ERROR);
        if (stringMap.isEmpty()){
            addPassengerToUser(user,passenger);
            userRepository.save(user);
            stringMap.put("success",DomainUserInfoConsts.PASSENGER_ADD_SUCCESS);
            return new MultiMessageResult(true,stringMap);
        }
        return new MultiMessageResult(false,stringMap);
    }

    public MultiMessageResult changePassenger(UserEntity user, PassengerEntity passenger){
        Map<String,String> stringMap = new LinkedHashMap();
        if (passenger.getPassengerName().isEmpty())
            stringMap.put("passengerNameError",DomainUserInfoConsts.PASSENGER_NAME_ERROR);
        if (passenger.getPassengerType().isEmpty())
            stringMap.put("passengerTypeError",DomainUserInfoConsts.PASSENGER_TYPE_ERROR);
        if (passenger.getSex().isEmpty())
            stringMap.put("passengerSexError",DomainUserInfoConsts.PASSENGER_SEX_ERROR);
        if (stringMap.isEmpty()) {
            addPassengerToUser(user, passenger);
            reloadPassengerToUser(user, passenger);
            userRepository.save(user);
            stringMap.put("success",DomainUserInfoConsts.PASSENGER_CHANGE_SUCCESS);
            return new MultiMessageResult(true,stringMap);
        }
        return new MultiMessageResult(false,stringMap);
    }

    public SingleMessageResult deletePassenger(UserEntity user, PassengerEntity passenger){
        if (removePassengerToUser(user,passenger)) return new SingleMessageResult(true,DomainUserInfoConsts.PASSENGER_DELETE_SUCCESS);
        return new SingleMessageResult(false,DomainUserInfoConsts.PASSENGER_NOT_FOUND_ERROR);
    }

    private void addPassengerToUser(UserEntity user, PassengerEntity passenger){
        user.getPassengersByUserNo().add(passenger);
    }

    private void reloadPassengerToUser(UserEntity user, PassengerEntity passenger){
        for (PassengerEntity passengerEntity : user.getPassengersByUserNo()) {
            if (passengerEntity.getPassengerNo().equals(passenger.getPassengerNo())){
                user.getPassengersByUserNo().remove(passengerEntity);
                user.getPassengersByUserNo().add(passenger);
                return;
            }
        }
        addPassenger(user,passenger);
    }

    private boolean removePassengerToUser(UserEntity user, PassengerEntity passenger){
        for (PassengerEntity passengerEntity : user.getPassengersByUserNo()) {
            if (passengerEntity.getPassengerNo().equals(passenger.getPassengerNo())){
                user.getPassengersByUserNo().remove(passengerEntity);
                return true;
            }
        }
        return false;
    }

}
