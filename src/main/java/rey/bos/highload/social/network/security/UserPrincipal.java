package rey.bos.highload.social.network.security;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import rey.bos.highload.social.network.io.entity.Authority;
import rey.bos.highload.social.network.io.entity.Role;
import rey.bos.highload.social.network.io.entity.User;

import java.util.Collection;
import java.util.HashSet;

@Getter
public class UserPrincipal implements UserDetails {

    @Getter(value=AccessLevel.PRIVATE)
    private final User user;
    @Getter(value=AccessLevel.PRIVATE)
    private final Collection<Role> roles;
    @Getter(value=AccessLevel.PRIVATE)
    private final Collection<Authority> authorityEntities;
    private final String userId;

    public UserPrincipal(User user, Collection<Role> roles, Collection<Authority> authorityEntities) {
        this.user = user;
        this.roles = roles;
        this.authorityEntities = authorityEntities;
        this.userId = user.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        if (CollectionUtils.isEmpty(roles)) {
            return authorities;
        }
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        authorityEntities.forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getName())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
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
}
