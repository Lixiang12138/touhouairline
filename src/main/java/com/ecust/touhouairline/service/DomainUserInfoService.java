package com.ecust.touhouairline.service;

import com.ecust.touhouairline.consts.DomainUserInfoConsts;
import com.ecust.touhouairline.consts.LoginConsts;
import com.ecust.touhouairline.entity.PassengerEntity;
import com.ecust.touhouairline.entity.UserEntity;
import com.ecust.touhouairline.repository.PassengerRepository;
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
    @Autowired
    PassengerRepository passengerRepository;

    public SingleMessageResult changeUserInfo(UserEntity user){
        SingleMessageResult result = checkUser(user);
        if (!result.isSuccess()) return result;
        userRepository.save(user);
        return new SingleMessageResult(true,DomainUserInfoConsts.USER_CHANGE_SUCCESS);
    }

    private SingleMessageResult checkUser(UserEntity user){
        if (!userRepository.existsById(user.getUserName())) return new SingleMessageResult(false, LoginConsts.USERNAME_NOT_FOUND_ERROR);
        if (user.getPassword().length() < 6 || user.getPassword().length() > 33) new SingleMessageResult(false,LoginConsts.PASSWORD_LENGTH_ERROR);
        if (user.getNickName().isEmpty()) new SingleMessageResult(false,LoginConsts.NICKNAME_ERROR);
        if (user.getEmail().isEmpty()) return new SingleMessageResult(false,LoginConsts.EMAIL_ERROR);
        if (user.getUserPhone().isEmpty()) new SingleMessageResult(false,LoginConsts.PHONE_ERROR);
        return new SingleMessageResult(true,null);
    }

    public ResultWithSingleMessage<Collection<PassengerEntity>> showPassages(UserEntity user){
        if(user.getPassengersByUserNo().isEmpty()) return new ResultWithSingleMessage(false,null,DomainUserInfoConsts.PASSENGER_NOT_FOUND);
        return new ResultWithSingleMessage(true,user.getPassengersByUserNo(),null);
    }

    public ResultWithSingleMessage<Collection<PassengerEntity>> showPassages(String username){
        if (!userRepository.existsById(username)) return new ResultWithSingleMessage<>(false,null,DomainUserInfoConsts.PASSENGER_NOT_FOUND_ERROR);
        UserEntity user = userRepository.getOne(username);
        return showPassages(user);
    }

    public MultiMessageResult addPassenger(String username, PassengerEntity passenger) {
        if (!userRepository.existsById(username)){
            Map<String,String> msg = new LinkedHashMap<>();
            msg.put("error",DomainUserInfoConsts.PASSENGER_NOT_FOUND_ERROR);
            return new MultiMessageResult(false, msg);
        }
        UserEntity user = userRepository.getOne(username);
        return addPassenger(user,passenger);
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
            stringMap.put("success",DomainUserInfoConsts.PASSENGER_ADD_SUCCESS);
            return new MultiMessageResult(true,stringMap);
        }
        return new MultiMessageResult(false,stringMap);
    }

    public MultiMessageResult changePassenger(String username, PassengerEntity passenger) {
        if (!userRepository.existsById(username)){
            Map<String,String> msg = new LinkedHashMap<>();
            msg.put("error",DomainUserInfoConsts.PASSENGER_NOT_FOUND_ERROR);
            return new MultiMessageResult(false, msg);
        }
        UserEntity user = userRepository.getOne(username);
        return changePassenger(user,passenger);
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
            reloadPassengerToUser(user, passenger);
            stringMap.put("success",DomainUserInfoConsts.PASSENGER_CHANGE_SUCCESS);
            return new MultiMessageResult(true,stringMap);
        }
        return new MultiMessageResult(false,stringMap);
    }

    public SingleMessageResult deletePassenger(String userName, Integer passengerNo){
        if (!userRepository.existsById(userName))  return new SingleMessageResult(false,DomainUserInfoConsts.PASSENGER_NOT_FOUND_ERROR);
        if (!passengerRepository.existsById(passengerNo))  return new SingleMessageResult(false,DomainUserInfoConsts.PASSENGER_NOT_FOUND_ERROR);
        UserEntity user = userRepository.getOne(userName);
        PassengerEntity passenger = passengerRepository.getOne(passengerNo);
        return deletePassenger(user,passenger);
    }

    public SingleMessageResult deletePassenger(UserEntity user, PassengerEntity passenger){
        if (removePassengerToUser(user,passenger)) {
            return new SingleMessageResult(true,DomainUserInfoConsts.PASSENGER_DELETE_SUCCESS);
        }
        return new SingleMessageResult(false,DomainUserInfoConsts.PASSENGER_NOT_FOUND_ERROR);
    }

    private void addPassengerToUser(UserEntity user, PassengerEntity passenger){
        passenger.setUserNo(user.getUserName());
        passengerRepository.save(passenger);
        user.getPassengersByUserNo().add(passenger);
        userRepository.save(user);
    }

    private void reloadPassengerToUser(UserEntity user, PassengerEntity passenger){
        for (PassengerEntity passengerEntity : user.getPassengersByUserNo()) {
            if (passengerEntity.getPassengerNo().equals(passenger.getPassengerNo())){
                user.getPassengersByUserNo().remove(passengerEntity);
                addPassenger(user,passenger);
                return;
            }
        }
    }

    private boolean removePassengerToUser(UserEntity user, PassengerEntity passenger){
        for (PassengerEntity passengerEntity : user.getPassengersByUserNo()) {
            if (passengerEntity.getPassengerNo().equals(passenger.getPassengerNo())){
                user.getPassengersByUserNo().remove(passengerEntity);
                userRepository.save(user);
                passengerRepository.delete(passenger);
                return true;
            }
        }
        return false;
    }

}
