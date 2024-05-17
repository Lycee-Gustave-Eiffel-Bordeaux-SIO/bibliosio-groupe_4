import { Injectable } from '@angular/core';
import {GenericService} from "../utils/generic.service";
import {Emprunt, StatutEmprunt} from "./Emprunt";
import {SerializedUrlObject} from "../utils/SerializedUrlObject";

@Injectable({
  providedIn: 'root'
})
export class EmpruntService extends GenericService<Emprunt>{

  protected override className = "Emprunt"
  protected override url = "http://localhost:8080/emprunts"

  getAllEmprunts() {
    return this.http.get<Emprunt[]>(this.url);
  }

  getEmpruntById(id: number) {
    return this.http.get<Emprunt>(this.url + "/" + id);
  }

  updateEmprunt(emprunt: Emprunt) {
    return this.http.put(this.url + "/" + emprunt.id, emprunt);
  }

  deleteEmprunt(emprunt: Emprunt) {
    return this.http.delete(this.url + "/" + emprunt.id);
  }

  createEmprunt(emprunt: Emprunt) {
    const requestBody = {
      id: 0,
      emprunteur: emprunt.emprunteur,
      exemplaire: emprunt.exemplaire,
      dateEmprunt: emprunt.dateDebut,
      dateEcheance: emprunt.dateEcheance,
      dateRetour: emprunt.dateRetour,
      statut: emprunt.statut
    };
    return this.http.post(this.url, requestBody);
  }
}
