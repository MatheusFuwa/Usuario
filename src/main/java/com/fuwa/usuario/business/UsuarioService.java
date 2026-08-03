package com.fuwa.usuario.business;

import com.fuwa.usuario.business.DTO.UsuarioDTO;
import com.fuwa.usuario.business.converter.UsuarioConverter;
import com.fuwa.usuario.infrastructure.entity.Usuario;
import com.fuwa.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.FileNameMap;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
