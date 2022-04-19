package com.census.support.system.menu;



import com.census.support.acl.user.User;
import com.census.support.acl.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemMenuAuthService {
    String loginUser;
    User loginUserInst;

    @Autowired
    private UserRepository userRepository;


    SystemMenuAuthService(){
//        this.loginUser = UserUtil.getLoginUser();
//        this.loginUserInst = this.userRepository.getUserByUsername(this.loginUser);
//        System.out.println(this.loginUserInst);
    }

    public boolean isAuthorized(String menuCode, String menuUrl) {
        //passing entity  name and username and then system auth res will be returned

        return true;
    }
}
