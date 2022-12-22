package com.coupons.couponsystem.security;

//@Configuration
public class CustomUserDetailsService {

//    @Bean
//    public UserDetailsService userDetailService(){
//
//    }

    //implements UserDetailsService {
//
//    private UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
//        User user=  userRepository.findByUserNameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(()->
//                new UsernameNotFoundException("user not found " + usernameOrEmail));
//
//        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
//
//    }
//
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
//
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
}
