## SpringSecurity相关
### 配置SpringSecurity登录注册
1.在项目中添加依赖，以Maven为例

``` xml
<dependency>
    <groupId>org.springframework.boot<groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
2.创建配置文件，继承WebSecurityConfigurerAdapter 类并实现相应方法
``` java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/loginReg.html")// 自定义登录页面
                .loginProcessingUrl("/login")// 登录请求的URL
                .successHandler(new LocalAuthenticationSuccessHandler()) // 登录验证成功后, 执行的内容
                .failureHandler(new LocalAuthenticationFailureHandler()) // 登录验证失败后, 执行的内容
                .permitAll();
        // 授权
        http.authorizeRequests()
                .antMatchers("/register").anonymous()
                .anyRequest().authenticated();// 所有请求页面必须授权后才能访问
        // 关闭csrf防护
        http.csrf().disable();
        // 禁用XFrame防护
        http.headers().frameOptions().disable();

        // 自定义注销信息
        http.logout() //
                .logoutUrl("/logout") // 登出验证api
//              .logoutSuccessHandler(new LocalLogoutSuccessHandler()) // 登录验证成功后, 执行的内容
//              .logoutSuccessUrl("/loginReg.html") // 登录验证成功后, 跳转的页面, 如果自定义返回内容, 请使用logoutSuccessHandler方法
                .deleteCookies("JSESSIONID") // 退出登录后需要删除的cookies名称
                .deleteCookies("username") // 退出登录后需要删除的cookies名称
                .invalidateHttpSession(true) // 退出登录后, 会话失效
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 放行静态资源，不走 Spring Security 过滤器链
        web.ignoring().antMatchers("/css/**", "/js/**", "/bootstrap/**", "/favicon.ico");
    }

}
```
3.实现UserDetailsService, 将数据库查到的密码封装到UserDetails并返回

``` java
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginVO vo = userMapper.getUserLoginVO(username);
        String role = vo.getRoleName();
        // 如果查询不到用户，提示不存在
        if (ObjectUtils.isEmpty(vo)) throw new UsernameNotFoundException("账号错误或用户不存在");
        // 根据查到的角色，创建相应的角色
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        // 这里传入数据库查到的密码，security框架会自动校验
        UserDetails userDetails = new User(username, vo.getPassword(),
                simpleGrantedAuthorities);
        return userDetails;
    }
}
```
4.[可选] 自定义登录成功Handler，需实现AuthenticationSuccessHandler接口

``` java
public class LocalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        //获取当前用户的信息
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        // 将用户名存到cookie中，返回给前端
        Cookie cookie = new Cookie("username", user.getUsername());
        httpServletResponse.addCookie(cookie);
        httpServletResponse.getWriter().print(RespVO.ok("ok"));
    }
}
```
5.[可选]自定义登录失败Handler，实现AuthenticationFailureHandler
``` java
public class LocalAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().print("error");
    }
}
```

6.注册使用PasswordEncode加密，写个配置类，并在注册服务中使用

``` java
@Configuration
public class PasswordConfig {
    @Bean
    public PasswordEncoder getPw(){
        return new BCryptPasswordEncoder(10);//加密强度，默认10
    }
}
```


### 参考文章
1.[Spring Security使用详解](https://www.hangge.com/blog/cache/detail_2717.html)

2.[Spring Security自定义登录验证及登录返回结果](https://blog.csdn.net/dahaiaixiaohai/article/details/107375939)

3.[Spring Security到底在哪里进行密码方式认证](https://blog.csdn.net/chengqiuming/article/details/103282586)