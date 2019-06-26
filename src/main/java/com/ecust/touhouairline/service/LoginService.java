package com.ecust.touhouairline.service;

import com.ecust.touhouairline.consts.LoginConsts;
import com.ecust.touhouairline.entity.CharacterEntity;
import com.ecust.touhouairline.entity.UserEntity;
import com.ecust.touhouairline.repository.CharacterRepository;
import com.ecust.touhouairline.repository.UserRepository;
import com.ecust.touhouairline.utils.ResultWithSingleMessage;
import com.ecust.touhouairline.utils.SingleMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚迟亮
 * 创建日期：2019-6-19
 **/
@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CharacterRepository characterReopository;

    /**
     * 客户或管理员登录
     * @param AccountName 账户名
     * @param Password 密码
     * @param isCustomer 如果是客户登录则为true
     *                   如果是管理员登陆则为false
     * @return 成功返回Success，失败返回"用户名或密码错误"
     */
    public ResultWithSingleMessage<UserEntity> Login(String AccountName, String Password, boolean isCustomer){
        UserEntity user = userRepository.findByUserNameAndPassword(AccountName,Password);
        if(user == null){
            return new ResultWithSingleMessage<>(false, null, LoginConsts.LOGIN_ERROR);
        }
        else{
            //如果是客户登录且账户类型确实是客户则返回登录成功
            if(isCustomer && user.getCharacterByUserNo().getCharacterName().equals("客户")){
                return new ResultWithSingleMessage<>(true, user, LoginConsts.SUCCESS);
            }
            //如果管理端登录且账户类型不为客户也返回成功
            else if(!isCustomer && !user.getCharacterByUserNo().getCharacterName().equals("客户")){
                return new ResultWithSingleMessage<>(true, user, LoginConsts.SUCCESS);
            }
            //否则是客户登录管理员账户或管理员登陆客户账户，返回登录失败
            else{
                return new ResultWithSingleMessage<>(false,null,LoginConsts.LOGIN_ERROR);
            }
        }
    }

    /**
     * @param userEntity 封装好的用户信息
     * @return 成功返回success,失败返回失败原因
     */
    public SingleMessageResult register(UserEntity userEntity){
        SingleMessageResult checkUser = checkUserWhenRegister(userEntity);
        if (checkUser.isSuccess()){
            CharacterEntity characterEntity = characterReopository.findByCharacterName("客户");
            userEntity.setCharacterByUserNo(characterEntity);
            userRepository.save(userEntity);
            return new SingleMessageResult(true,LoginConsts.SUCCESS);
        }
        else return checkUser;
    }

    private SingleMessageResult checkUserWhenRegister(UserEntity user){
        if (userRepository.existsById(user.getUserName())) return new SingleMessageResult(false,LoginConsts.USERNAME_EXISTS_ERROR);
        if (user.getPassword().length() < 6 || user.getPassword().length() > 33) new SingleMessageResult(false,LoginConsts.PASSWORD_LENGTH_ERROR);
        if (user.getNickName().isEmpty()) new SingleMessageResult(false,LoginConsts.NICKNAME_ERROR);
        if (user.getEmail().isEmpty()) return new SingleMessageResult(false,LoginConsts.EMAIL_ERROR);
        if (user.getUserPhone().isEmpty()) new SingleMessageResult(false,LoginConsts.PHONE_ERROR);
        return new SingleMessageResult(true,null);
    }

}
