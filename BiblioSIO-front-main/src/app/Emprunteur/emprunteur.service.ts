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

  public trierParClasseCroissant(emprunteurs: Emprunteur[]): Emprunteur[] {
    return emprunteurs.sort((a, b) => {
      // Ordre de tri spécifié
      const ordreTri = [
        Classe.SIO1A,
        Classe.SIO1B,
        Classe.SIO2A,
        Classe.SIO2B,
      ];

      // Trouver l'index de la classe de l'emprunteur a
      const indexA = a.classe!== undefined? ordreTri.indexOf(a.classe) : -1;
      // Trouver l'index de la classe de l'emprunteur b
      const indexB = b.classe!== undefined? ordreTri.indexOf(b.classe) : -1;

      // Comparer les indices pour déterminer l'ordre de tri
      if (indexA < indexB) {
        return -1;
      } else if (indexA > indexB) {
        return 1;
      } else {
        // Si les classes sont identiques, garder l'ordre original
        return 0;
      }
    });
  }

  public trierParClasseDecroissant(emprunteurs: Emprunteur[]): Emprunteur[] {
    return emprunteurs.sort((a, b) => {
      // Ordre de tri spécifié
      const ordreTri = [
        Classe.SIO1A,
        Classe.SIO1B,
        Classe.SIO2A,
        Classe.SIO2B,
      ];

      // Trouver l'index de la classe de l'emprunteur a
      const indexA = a.classe!== undefined? ordreTri.indexOf(a.classe) : -1;
      // Trouver l'index de la classe de l'emprunteur b
      const indexB = b.classe!== undefined? ordreTri.indexOf(b.classe) : -1;

      // Comparer les indices pour déterminer l'ordre de tri en ordre décroissant
      if (indexA < indexB) {
        return 1;
      } else if (indexA > indexB) {
        return -1;
      } else {
        // Si les classes sont identiques, garder l'ordre original
        return 0;
      }
    });
  }

  public trierParNomEmprunteurCroissant(emprunteurs: Emprunteur[]): Emprunteur[] {
    return emprunteurs.sort((a, b) => {
      const nomA = a.nomEmprunteur?? "";
      const nomB = b.nomEmprunteur?? "";
      return nomA.localeCompare(nomB); // Utilise localeCompare pour un tri sensible à la langue
    });
  }

  public trierParNomEmprunteurDecroissant(emprunteurs: Emprunteur[]): Emprunteur[] {
    return emprunteurs.sort((a, b) => {
      const nomA = a.nomEmprunteur?? "";
      const nomB = b.nomEmprunteur?? "";
      return nomB.localeCompare(nomA); // Inverse l'ordre de comparaison pour un tri décroissant
    });
  }

  public trierNomsFamillesCroissantClasseDonnee(emprunteurs: Emprunteur[], classeSpecifique: Classe): Emprunteur[] {
    return emprunteurs
      .filter(emprunteur => emprunteur.classe === classeSpecifique)
      .sort((a, b) => a.nomEmprunteur!.localeCompare(b.nomEmprunteur?? ""));
  }

  public trierNomsFamillesDecroissantClasseDonnee(emprunteurs: Emprunteur[], classeSpecifique: Classe): Emprunteur[] {
    return emprunteurs
      .filter(emprunteur => emprunteur.classe === classeSpecifique)
      .sort((a, b) => b.nomEmprunteur!.localeCompare(a.nomEmprunteur?? ""));
  }
}
