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
      moisParution: exemplaire.moisParution,
      anneeParution: exemplaire.anneeParution,
      statut: exemplaire.statut,
      revue: exemplaire.revue,
    };
    return this.http.post(this.url, requestBody);
  }

  // Structure de données pour les mois
  moisDeLAnnee = [
    { nom: "Janvier", numero: 0 },
    { nom: "Février", numero: 1 },
    { nom: "Mars", numero: 2 },
    { nom: "Avril", numero: 3 },
    { nom: "Mai", numero: 4 },
    { nom: "Juin", numero: 5 },
    { nom: "Juillet", numero: 6 },
    { nom: "Août", numero: 7 },
    { nom: "Septembre", numero: 8 },
    { nom: "Octobre", numero: 9 },
    { nom: "Novembre", numero: 10 },
    { nom: "Décembre", numero: 11 }
  ];

// Fonction pour convertir les années et les mois en nombres
  public convertirDate(exemple: Exemplaire): number {
    let annee = 0;
    let mois = 0;

    if (exemple.anneeParution!== undefined && exemple.anneeParution!== null) {
      annee = Number(exemple.anneeParution);
    }

    if (exemple.moisParution!== undefined && exemple.moisParution!== null) {
      const moisTrouve = this.moisDeLAnnee.find(mois => mois.nom === exemple.moisParution);
      mois = moisTrouve?.numero?? 0;
    }

    return annee * 100 + mois;
  }

// Fonction pour trier les dates
  public trierDatesCroissant(exemplaires: Exemplaire[]): Exemplaire[] {
    return exemplaires.sort((a, b) => {
      const dateA = this.convertirDate(a);
      const dateB = this.convertirDate(b);

      // Compare les dates "vides" en premier
      if (dateA === 0 && dateB === 0) {
        return 0;
      } else if (dateA === 0) {
        return 1;
      } else if (dateB === 0) {
        return -1;
      }

      // Compare les dates normales
      return dateA - dateB;
    });
  }

  public trierDatesDecroissant(exemplaires: Exemplaire[]): Exemplaire[] {
    return exemplaires.sort((a, b) => {
      const dateA = this.convertirDate(a);
      const dateB = this.convertirDate(b);

      // Compare les dates "vides" en premier
      if (dateA === 0 && dateB === 0) {
        return 0;
      } else if (dateA === 0) {
        return -1;
      } else if (dateB === 0) {
        return 1;
      }

      // Compare les dates normales en ordre décroissant
      return dateB - dateA; // Inversion de la comparaison pour un tri décroissant
    });
  }


  public trierIdsCroissant(exemplaires: Exemplaire[]): Exemplaire[] {
    return exemplaires.sort((a, b) =>{
      const idA = Number(a.id);
      const idB = Number(b.id);

      return idA - idB;
    });
  }

  public trierIdsDecroissant(exemplaires: Exemplaire[]): Exemplaire[] {
    return exemplaires.sort((a, b) =>{
      const idA = Number(a.id);
      const idB = Number(b.id);

      return idB - idA;
    });
  }
}
