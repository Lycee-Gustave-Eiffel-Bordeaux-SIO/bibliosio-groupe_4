import { Injectable } from '@angular/core';
import {GenericService} from "../utils/generic.service";
import {Exemplaire} from "./Exemplaire";
import {SerializedUrlObject} from "../utils/SerializedUrlObject";

@Injectable({
  providedIn: 'root'
})
export class ExemplaireService extends GenericService<Exemplaire> {
  protected override className = "Exemplaire"
  protected override url = "http://localhost:8080/exemplaires"

  getAllExemplaires() {
    return this.http.get<Exemplaire[]>(this.url);
  }

  getExemplaireById(id: Number) {
    return this.http.get<Exemplaire>(this.url + "/" + id);
  }

  updateExemplaire(exemplaire: Exemplaire) {
    return this.http.put(this.url + "/" + exemplaire.id, exemplaire);
  }

  deleteExemplaire(exemplaire: Exemplaire) {
    return this.http.delete(this.url + "/" + exemplaire.id);
  }

  createExemplaire(exemplaire: Exemplaire) {
    const requestBody = {
      id: 0,
      titre: exemplaire.titre,
      dateParution: exemplaire.dateParution,
      statut: exemplaire.statut,
      revue: exemplaire.revue,
    };
    return this.http.post(this.url, requestBody);
  }
}
