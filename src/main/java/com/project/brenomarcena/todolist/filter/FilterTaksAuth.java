package com.project.brenomarcena.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.brenomarcena.todolist.user.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaksAuth  extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

            var sevletPath = request.getServletPath();

            if (sevletPath.startsWith("/tasks/")){

                var authorization = request.getHeader("Authorization");

                var authEncoded = authorization.substring("Basic".length()).trim();

                byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

                String authString = new String(authDecoded);

                String[] credentials = authString.split(":");

                String username = credentials[0];
                String password = credentials[1];

                var user = this.userRepository.findByUsername(username);

                if (user == null) {
                    response.sendError(401, "Usuário não encontrado");
                }else{
                    var passwordVeryfy = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if (passwordVeryfy.verified) {
                        request.setAttribute("idUser", user.getId());
                        filterChain.doFilter(request, response);
                    }else{
                        response.sendError(401, "Senha inválida");
                    }
                }

                

            }else{
                filterChain.doFilter(request, response);
            }


        

    }


}
