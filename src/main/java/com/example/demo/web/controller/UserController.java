package com.example.demo.web.controller;

import com.example.demo.dto.user.CreateUserDto;
import com.example.demo.dto.result.Result;
import com.example.demo.dto.user.UpdateUserDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author vghosthunter
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    private Result get(@PathVariable(name = "id") String id) {
        return Result.succeeded(userService.get(id));
    }

    @GetMapping("/info")
    private Result info() {
        return Result.succeeded(userService.userInfo());
    }

    @GetMapping("/{page}/{size}")
    private Result get(@RequestBody(required = false) Map searchMap, @PathVariable int page, @PathVariable int size) {
        return Result.succeeded(userService.getPage(searchMap, page, size));
    }

    @PostMapping("/login")
    private Result login(@RequestBody CreateUserDto user, HttpServletRequest request){
        UserDto userDto = userService.login(user.getLoginName(), user.getPassword() );
        HttpSession session = request.getSession(false);
        if(session != null) {
            //防止session定向攻击
            session.invalidate();
        }
        request.getSession().setAttribute("user", userDto);
        return Result.succeeded(userDto);
    }

    @GetMapping("/logout")
    private Result logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return Result.succeeded();
    }

    @PostMapping
    private Result create(@Valid @RequestBody CreateUserDto userDto){
        userService.create(userDto);
        return Result.succeeded();
    }

    @PutMapping
    private Result update(@Valid @RequestBody UpdateUserDto userDto) {
        userService.update(userDto);
        return Result.succeeded();
    }
}
