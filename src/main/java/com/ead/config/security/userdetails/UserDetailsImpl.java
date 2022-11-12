package com.ead.config.security.userdetails;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private UUID id;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID id, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    private static SimpleGrantedAuthority toSimpleGrantedAuthority(final String strAuthority) {
        return new SimpleGrantedAuthority(strAuthority);
    }

    private static Collection<? extends GrantedAuthority> toListGrantedAuthority(final String listAuthorityStr) {
        return Arrays.stream(listAuthorityStr.split(","))
                     .map(UserDetailsImpl::toSimpleGrantedAuthority)
                     .collect(Collectors.toSet());
    }

    public static UserDetailsImpl build(final UUID id, final String listAuthorityStr) {
        final var authorities = toListGrantedAuthority(listAuthorityStr);

        return new UserDetailsImpl(id, authorities);
    }
}
