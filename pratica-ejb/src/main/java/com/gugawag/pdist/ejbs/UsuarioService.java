package com.gugawag.pdist.ejbs;

import com.gugawag.pdist.model.Usuario;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

@Stateless(name = "usuarioService")
@Remote
public class UsuarioService {

    @EJB
    private UsuarioDAO usuarioDao;

    public List<Usuario> listar() {
        return usuarioDao.listar();
    }

    public void inserir(long id, String nome) {
        Usuario novoUsuario = new Usuario(id, nome);
        usuarioDao.inserir(novoUsuario);
        if (id==3L) {
            throw new RuntimeException("Menor de idade não permitido!");
        }
        if (id == 4L) {
            novoUsuario.setNome(nome + " alterado");
        }
    }
}
