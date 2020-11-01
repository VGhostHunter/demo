package com.example.demo.web.service;

import com.example.demo.config.password.PasswordEncode;
import com.example.demo.exception.BusinessException;
import com.example.demo.web.dao.UserRepository;
import com.example.demo.dto.PageResult;
import com.example.demo.dto.user.CreateUserDto;
import com.example.demo.dto.user.UpdateUserDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.User;
import com.example.demo.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author vghosthunter
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncode passwordEncode;

    public UserService(UserRepository userRepository, PasswordEncode passwordEncode) {
        this.userRepository = userRepository;
        this.passwordEncode = passwordEncode;
    }

    public UserDto get(String id) {
        Optional<User> byId = userRepository.findById(id);
        if(!byId.isPresent()) {
            throw new EntityNotFoundException();
        }
        User user = byId.get();
        return user.toDto();
    }

    public UserDto userInfo(){
        UserDto userDto = currentUser();
        if(userDto == null) {
            throw new EntityNotFoundException();
        }
        return userDto;
    }

    public PageResult<UserDto> getPage(Map<String, String> searchMap, int page, int size) {
        Specification specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<User> all = userRepository.findAll(specification, pageRequest);
        List<UserDto> collect = all.getContent()
                .stream()
                .map(User::toDto)
                .collect(Collectors.toList());
        return new PageResult<UserDto>(all.getTotalElements(), collect);
    }

    public void create(CreateUserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(passwordEncode(userDto.getPassword()));
        userRepository.save(user);
    }

    public void update(UpdateUserDto userDto) {
        Optional<User> byId = userRepository.findById(userDto.getId());
        if(!byId.isPresent()) {
            throw new EntityNotFoundException();
        }
        User user = byId.get();
        BeanUtils.copyProperties(userDto, user, BeanUtil.getNullPropertyNames(userDto));
        userRepository.save(user);
    }

    public UserDto check(String username, String password){
        User user = userRepository.findByLoginName(username);
        if(user != null) {
            if(user.getPassword().equals(passwordEncode(password))) {
                return user.toDto();
            } else {
                throw new BusinessException("用户名或密码错误");
            }
        }
        return null;
    }

    public UserDto login(String username, String password) {
        UserDto userDto = check(username, password);
        if(userDto == null) {
            throw new EntityNotFoundException();
        }
        return userDto;
    }

    public UserDto currentUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        UserDto user = (UserDto) (servletRequestAttributes != null ? servletRequestAttributes.getRequest().getSession().getAttribute("user") : null);
        return user;
    }

    /**
     * 构建查询条件
     * @param searchMap * @return
     */
    private Specification<User> createSpecification(Map searchMap){
        if(searchMap == null){
            return null;
        }
        return (Specification<User>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicateList=new ArrayList<>();
            if(!StringUtils.isEmpty(searchMap.get("name"))){
                predicateList.add(cb.like( root.get("name").as(String.class), "%"+ (String)searchMap.get("name")+"%" ) );
            }
            return cb.and( predicateList.toArray( new Predicate[predicateList.size()]) );
        };
    }

    private String passwordEncode(String password) {
        return passwordEncode.encode(password);
    }

}
