package com.proyectoraul.demo.service;

import java.util.List;

import com.proyectoraul.demo.dto.PisoDTO;

public interface PisoService {
    PisoDTO crear(PisoDTO dto);

    List<PisoDTO> listar();

    PisoDTO buscarPorIdPiso(Integer IdPiso);

    PisoDTO actualizar(Integer IdPiso, PisoDTO dto);

    void eliminar(Integer IdPiso);
}
