package com.fuwa.usuario.business;

import com.fuwa.usuario.UsuarioApplication;
import com.fuwa.usuario.business.DTO.EnderecoDTO;
import com.fuwa.usuario.business.DTO.TelefoneDTO;
import com.fuwa.usuario.business.DTO.UsuarioDTO;
import com.fuwa.usuario.business.converter.UsuarioConverter;
import com.fuwa.usuario.infrastructure.entity.Endereco;
import com.fuwa.usuario.infrastructure.entity.Telefone;
import com.fuwa.usuario.infrastructure.entity.Usuario;

import com.fuwa.usuario.infrastructure.exeptions.ConflictExeption;
import com.fuwa.usuario.infrastructure.exeptions.ResorceNotFoundExeption;
import com.fuwa.usuario.infrastructure.repository.EnderecoRepository;
import com.fuwa.usuario.infrastructure.repository.TelefoneRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuaroPorEmail(String email){
        try{
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(() -> new ResorceNotFoundExeption(
                "Email não encontrado " + email))
        );
        }catch (ResorceNotFoundExeption e){
            throw new ResorceNotFoundExeption("Email não encontrado " + email);
        }
    }
    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto){
        String email =  jwtUtil.extractUsername(token.substring(7));

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResorceNotFoundExeption("Email não localizado"));

        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResorceNotFoundExeption("Id não encontrado" + idEndereco));

        Endereco endereco = usuarioConverter.updateendereco(enderecoDTO,entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResorceNotFoundExeption("Id não encontrado" + idTelefone));

        Telefone telefone = usuarioConverter.uptadeTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
    public EnderecoDTO CadastraEndereco(String token, EnderecoDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResorceNotFoundExeption("Email não localizado " + email));

        Endereco endereco =usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }

    public TelefoneDTO CadastraTelefone(String token, TelefoneDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResorceNotFoundExeption("Email não localizado " + email));
        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        Telefone telefoneEntity = telefoneRepository.save(telefone);
        return usuarioConverter.paraTelefoneDTO(telefoneEntity);
    }
}