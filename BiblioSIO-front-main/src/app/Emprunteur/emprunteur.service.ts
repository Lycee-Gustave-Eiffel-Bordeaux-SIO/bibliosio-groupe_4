import {Injectable} from '@angular/core';
import {Classe, Emprunteur} from "./Emprunteur";
import {GenericService} from "../utils/generic.service";
import {SerializedUrlObject} from "../utils/SerializedUrlObject";

@Injectable({
  providedIn: 'root'
})
export class EmprunteurService extends GenericService<Emprunteur>{

  protected override url = "http://localhost:8080/emprunteurs"
  protected override className = "Emprunteur"

  getAllEmprunteurs() {
    return this.http.get<Emprunteur[]>(this.url);
  }

  getEmprunteurById(id: Number) {
    return this.http.get<Emprunteur>(this.url + "/" + id);
  }

  updateEmprunteur(emprunteur: Emprunteur) {
    return this.http.put(this.url + "/" + emprunteur.id, emprunteur);
  }

  deleteEmprunteur(emprunteur: Emprunteur) {
    return this.http.delete(this.url + "/" + emprunteur.id);
  }

  createEmprunteur(emprunteur: Emprunteur) {
    const requestBody = {
      id: 0,
      nom: emprunteur.nomEmprunteur,
      prenom: emprunteur.prenomEmprunteur,
      mail: emprunteur.mailEmprunteur,
      promo: emprunteur.promo,
      classe: emprunteur.classe
    };
    return this.http.post(this.url, requestBody);
  }

}
