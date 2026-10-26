package com.proyectoraul.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.proyectoraul.demo.dto.PisoDTO;
import com.proyectoraul.demo.mapper.PisoMapper;
import com.proyectoraul.demo.model.Piso;
import com.proyectoraul.demo.repository.PisoRepository;
import com.proyectoraul.demo.util.AleatorioAlfanumerico;

@Service
public class PisoServiceImpl implements PisoService {

    private final PisoRepository repository;
    private final PisoMapper mapper;
    public AleatorioAlfanumerico aleatorio = new AleatorioAlfanumerico();

    public PisoServiceImpl(
            PisoRepository repository,
            PisoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PisoDTO crear(PisoDTO dto) {
        Integer logitudCodigo = 10;
        String codigo = aleatorio.ValoresAleatorios(logitudCodigo);
        do {
            codigo = aleatorio.ValoresAleatorios(logitudCodigo);

        } while (repository.existsByCodigo(codigo) == true);
        dto.setCodigoInvitacion(codigo);
        Piso piso = mapper.toEntity(dto);

        // el ID lo genera PostgreSQL
        piso.setIdPiso(null);

        // guarda el piso en la base de datos
        Piso guardado = repository.save(piso);

        // devuelve el piso guardado, con su ID
        return mapper.toDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PisoDTO> listar() {
        return repository.listarPisos()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PisoDTO buscarPorIdPiso(Integer IdPiso) {
        Piso piso = repository.buscarPorIdPisoSQL(IdPiso)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Piso no encontrado"));

        return mapper.toDTO(piso);
    }

    @Override
    @Transactional
    public PisoDTO actualizar(Integer IdPiso, PisoDTO dto) {
        repository.buscarPorIdPisoSQL(IdPiso)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Piso no encontrado"));

        int filas = repository.actualizarPiso(
                IdPiso,
                dto.getNombre());

        if (filas == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Piso no encontrado");
        }

        return mapper.toDTO(
                repository.buscarPorIdPisoSQL(IdPiso).orElseThrow());
    }

    @Override
    @Transactional
    public void eliminar(Integer IdPiso) {
        int filas = repository.eliminarPiso(IdPiso);

        if (filas == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Piso no encontrado");
        }
    }
}
