package com.fuwa.usuario.business;

import com.fuwa.usuario.UsuarioApplication;
import com.fuwa.usuario.business.DTO.UsuarioDTO;
import com.fuwa.usuario.business.converter.UsuarioConverter;
import com.fuwa.usuario.infrastructure.entity.Usuario;
import com.fuwa.usuario.infrastructure.exeptions.ConflictExeption;
import com.fuwa.usuario.infrastructure.exeptions.ResorceNotFoundExeption;
import com.fuwa.usuario.infrastructure.repository.UsuarioRepository;
import com.fuwa.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.FileNameMap;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictExeption("Email já cadastrado" + email);
            }
        } catch (ConflictExeption e){
            throw new ConflictExeption("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuaroPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResorceNotFoundExeption(
                "Email não encontrado" + email
        ));
    }
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
        String email =  jwtUtil.extractUsername(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResorceNotFoundExeption("Email não localizado"));

        Usuario usuario = usuarioConverter.uptadeUsuario(dto, usuarioEntity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}

