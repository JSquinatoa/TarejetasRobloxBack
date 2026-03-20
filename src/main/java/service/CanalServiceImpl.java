package service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import repository.ICanalRepo;
import repository.model.Canal;
import service.exception.CanalNotFoundException;
import service.exception.CanalDataAccessException;

@ApplicationScoped
public class CanalServiceImpl implements ICanalService {

    @Inject
    private ICanalRepo canalRepo;

    @Override
    public void guardarCanal(Canal canal) {
        try {
            this.canalRepo.insertarCanal(canal);
        } catch (Exception e) {
            throw new CanalDataAccessException("Error al guardar el canal: " + e.getMessage(), e);
        }
    }

    @Override
    public Canal buscarPorId(Integer id) {
        try {
            Canal canal = this.canalRepo.seleccionarPorId(id);
            if (canal == null) {
                throw new CanalNotFoundException("Canal con ID " + id + " no encontrado");
            }
            return canal;
        } catch (CanalNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CanalDataAccessException("Error al buscar el canal por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Canal> buscarTodos() {
        try {
            return this.canalRepo.seleccionarTodos();
        } catch (Exception e) {
            throw new CanalDataAccessException("Error al buscar todos los canales: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Canal canal) {
        try {
            this.canalRepo.actualizar(canal);
        } catch (Exception e) {
            throw new CanalDataAccessException("Error al actualizar el canal: " + e.getMessage(), e);
        }
    }

    @Override
    public void borrarPorId(Integer id) {
        try {
            Canal canal = this.canalRepo.seleccionarPorId(id);
            if (canal == null) {
                throw new CanalNotFoundException("Canal con ID " + id + " no encontrado, no se puede borrar");
            }
            this.canalRepo.eliminarPorId(id);
        } catch (CanalNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CanalDataAccessException("Error al borrar el canal por ID: " + e.getMessage(), e);
        }
    }

}
