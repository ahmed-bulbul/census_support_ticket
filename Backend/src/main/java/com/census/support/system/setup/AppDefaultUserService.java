package com.census.support.system.setup;

import com.census.support.acl.role.Role;
import com.census.support.acl.role.RoleRepository;
import com.census.support.acl.user.User;
import com.census.support.acl.user.UserRepository;
import com.census.support.system.constants.SystemRole;
import com.census.support.system.counter.SystemCounter;
import com.census.support.system.counter.SystemCounterRepository;
import com.census.support.system.menu.SystemMenu;
import com.census.support.system.menu.SystemMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppDefaultUserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SystemCounterRepository counterRepository;

    @Autowired
    private SystemMenuRepository menuRepository;

    public void createRoles(){

        if (!roleRepository.findByAuthority(String.valueOf(SystemRole.ROLE_SUPER_ADMIN)).isPresent()) {
            roleRepository.save(new Role(1L, String.valueOf(SystemRole.ROLE_SUPER_ADMIN), "ROLE_SUPER_ADMIN",new Date(),"SYSTEM"));
        }
        if (!roleRepository.findByAuthority(String.valueOf(SystemRole.ROLE_ADMIN)).isPresent()) {
            roleRepository.save(new Role(2L, String.valueOf(SystemRole.ROLE_ADMIN), "ROLE_ADMIN",new Date(),"SYSTEM"));
        }
        if (!roleRepository.findByAuthority(String.valueOf(SystemRole.ROLE_USER)).isPresent()) {
            roleRepository.save(new Role(3L,String.valueOf(SystemRole.ROLE_USER), "ROLE_USER",new Date(),"SYSTEM"));
        }
        if (!roleRepository.findByAuthority(String.valueOf(SystemRole.ROLE_BBS_USER)).isPresent()) {
            roleRepository.save(new Role(4L,String.valueOf(SystemRole.ROLE_BBS_USER), "ROLE_BBS_USER",new Date(),"SYSTEM"));
        }
        if(!roleRepository.findByAuthority(String.valueOf(SystemRole.ROLE_TIRE1_USER)).isPresent()){
            roleRepository.save(new Role(5L,String.valueOf(SystemRole.ROLE_TIRE1_USER), "ROLE_TIRE1_USER",new Date(),"SYSTEM"));
        }
        if(!roleRepository.findByAuthority(String.valueOf(SystemRole.ROLE_TIRE2_USER)).isPresent()){
            roleRepository.save(new Role(6L,String.valueOf(SystemRole.ROLE_TIRE2_USER), "ROLE_TIRE2_USER",new Date(),"SYSTEM"));
        }
    }

    public void createUser(){
        Role roleSuperAdmin = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_SUPER_ADMIN));
        Role roleAdmin = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_ADMIN));
        Role roleUser = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_USER));
        Role roleBbsUser = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_BBS_USER));
        Role roleTire1User = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_TIRE1_USER));
        Role roleTire2User = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_TIRE2_USER));

        //super admin
        if(!userRepository.findByUsername("super-admin").isPresent()){
            Set<Role> rolesSuperAdminSet = new HashSet<>();
            User user = new User();

            user.setUsername("super-admin");
            user.setPassword(bCryptPasswordEncoder.encode("super-admin"));
            user.setPhone("01678862524");
            rolesSuperAdminSet.add(roleSuperAdmin);
            user.setRoles(rolesSuperAdminSet);
            user.setCreationDateTime(new Date());
            user.setCreationUser("SYSTEM");
            this.userRepository.save(user);

        }

        //bbs user
        if(!userRepository.findByUsername("bbs-user").isPresent()){
            Set<Role> rolesBbsUserSet = new HashSet<>();
            User user = new User();

            user.setUsername("bbs-user");
            user.setPassword(bCryptPasswordEncoder.encode("bbs-user"));
            user.setPhone("01678862525");
            rolesBbsUserSet.add(roleBbsUser);
            user.setRoles(rolesBbsUserSet);
            user.setCreationDateTime(new Date());
            user.setCreationUser("SYSTEM");
            this.userRepository.save(user);
        }

        //tire1 user
        if(!userRepository.findByUsername("tire1-user").isPresent()){
            Set<Role> rolesTire1UserSet = new HashSet<>();
            User user = new User();

            user.setUsername("tire1-user");
            user.setPassword(bCryptPasswordEncoder.encode("tire1-user"));
            user.setPhone("01678862526");
            rolesTire1UserSet.add(roleTire1User);
            user.setRoles(rolesTire1UserSet);
            user.setCreationDateTime(new Date());
            user.setCreationUser("SYSTEM");
            this.userRepository.save(user);
        }
        //tire1 user2
        if(!userRepository.findByUsername("tire1-user2").isPresent()){
            Set<Role> rolesTire1UserSet = new HashSet<>();
            User user = new User();

            user.setUsername("tire1-user2");
            user.setPassword(bCryptPasswordEncoder.encode("tire1-user2"));
            user.setPhone("01678862528");
            rolesTire1UserSet.add(roleTire1User);
            user.setRoles(rolesTire1UserSet);
            user.setCreationDateTime(new Date());
            user.setCreationUser("SYSTEM");
            this.userRepository.save(user);
        }

        //tire2 user
        if(!userRepository.findByUsername("tire2-user").isPresent()){
            Set<Role> rolesTire2UserSet = new HashSet<>();
            User user = new User();

            user.setUsername("tire2-user");
            user.setPassword(bCryptPasswordEncoder.encode("tire2-user"));
            user.setPhone("01678862527");
            rolesTire2UserSet.add(roleTire2User);
            user.setRoles(rolesTire2UserSet);
            user.setCreationDateTime(new Date());
            user.setCreationUser("SYSTEM");
            this.userRepository.save(user);
        }
    }

    public void CreateTicketCodeCounter(){
        if (!counterRepository.getByCode("TICKET_CODE_CNT").isPresent()){
            SystemCounter systemCounter = new SystemCounter();
            systemCounter.setId(1L);
            systemCounter.setActive(true);
            systemCounter.setCode("TICKET_CODE_CNT");
            systemCounter.setCounterName("Ticket Code");
            systemCounter.setCounterType("TICKET_CODE");
            systemCounter.setNextNumber(1L);
            systemCounter.setNumerationType("2");
            systemCounter.setPrefix("T");
            systemCounter.setPrefixSeparator("-");
            systemCounter.setStep(1);
            systemCounter.setCounterWidth(9);
            counterRepository.save(systemCounter);
        }
    }

    public void createMenu() {


        //Ticket root menu
        if (!menuRepository.findByCode("TICKET").isPresent()) {
            SystemMenu menu = new SystemMenu();
            menu.setId(1L);
            menu.setCode("TICKET");
            menu.setDescription("Ticket");
            menu.setOpenUrl("/ticket");
            menu.setIconHtml("fa fa-ticket");
            menu.setSequence(1);
            menu.setHasChild(true);
            menu.setVisibleToAll(true);
            menu.setLeftSideMenu(true);
            menu.setIsActive(true);
            menu.setCreationDateTime(new Date());
            menu.setCreationUser("SYSTEM");
            menuRepository.save(menu);
        }

        // ticket-BBS child menu
        if (!menuRepository.findByCode("BBS").isPresent()) {
            SystemMenu menu = new SystemMenu();
            menu.setId(2L);
            menu.setCode("BBS");
            menu.setDescription("BBS");
            menu.setOpenUrl("/ticket/bbs/list");
            menu.setIconHtml("");
            menu.setSequence(2);
            menu.setHasChild(false);
            menu.setVisibleToAll(true);
            menu.setLeftSideMenu(true);
            menu.setIsActive(true);
            menu.setCreationDateTime(new Date());
            menu.setCreationUser("SYSTEM");
            menu.setParentMenu((SystemMenu) menuRepository.findByCode("TICKET").get());
            menuRepository.save(menu);
        }
        // ticket-TIRE1 child menu
        if (!menuRepository.findByCode("TIER1").isPresent()) {
            SystemMenu menu = new SystemMenu();
            menu.setId(3L);
            menu.setCode("TIER1");
            menu.setDescription("Tier1");
            menu.setOpenUrl("/ticket/tire1/list");
            menu.setIconHtml("");
            menu.setSequence(3);
            menu.setHasChild(false);
            menu.setVisibleToAll(true);
            menu.setLeftSideMenu(true);
            menu.setIsActive(true);
            menu.setCreationDateTime(new Date());
            menu.setCreationUser("SYSTEM");
            menu.setParentMenu((SystemMenu) menuRepository.findByCode("TICKET").get());
            menuRepository.save(menu);

        }

        // ticket-TIRE2 child menu
        if (!menuRepository.findByCode("TIER2").isPresent()) {
            SystemMenu menu = new SystemMenu();
            menu.setId(4L);
            menu.setCode("TIER2");
            menu.setDescription("Tier2");
            menu.setOpenUrl("/ticket/tire2/list");
            menu.setIconHtml("");
            menu.setSequence(4);
            menu.setHasChild(false);
            menu.setVisibleToAll(true);
            menu.setLeftSideMenu(true);
            menu.setIsActive(true);
            menu.setCreationDateTime(new Date());
            menu.setCreationUser("SYSTEM");
            menu.setParentMenu((SystemMenu) menuRepository.findByCode("TICKET").get());
            menuRepository.save(menu);

        }


        if (!menuRepository.findByCode("User").isPresent()) {
            SystemMenu menu = new SystemMenu();
            menu.setId(5L);
            menu.setCode("USER");
            menu.setDescription("User");
            menu.setOpenUrl("/users/user/list");
            menu.setIconHtml("fa fa-user");
            menu.setSequence(5);
            menu.setHasChild(false);
            menu.setVisibleToAll(true);
            menu.setLeftSideMenu(true);
            menu.setIsChild(true);
            menu.setIsActive(true);
            menu.setCreationDateTime(new Date());
            menu.setCreationUser("SYSTEM");
            menuRepository.save(menu);
        }
    }



    //@PostConstruct
    public void createDefaultUserAndRoles(){
        this.createRoles();
        this.createUser();
        this.createMenu();
        this.CreateTicketCodeCounter();
    }


}
