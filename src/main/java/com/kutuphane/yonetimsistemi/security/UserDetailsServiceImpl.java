package com.kutuphane.yonetimsistemi.security;

import com.kutuphane.yonetimsistemi.entity.Kullanici;
import com.kutuphane.yonetimsistemi.repository.KullaniciRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final KullaniciRepository kullaniciRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + email));
        var yetkiler = List.of(new SimpleGrantedAuthority(kullanici.getRol().name()));

        return new org.springframework.security.core.userdetails.User(
                kullanici.getEmail(),
                kullanici.getSifre(),
                yetkiler
        );
    }
}