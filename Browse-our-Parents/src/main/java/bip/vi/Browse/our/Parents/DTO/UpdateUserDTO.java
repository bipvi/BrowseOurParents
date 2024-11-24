package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.enums.Ruolo;

import java.io.Serializable;

public record UpdateUserDTO(String username, String password, String avatar, Ruolo ruolo){
}