import {SerializedUrlObject} from "../utils/SerializedUrlObject";

export interface Emprunt {
  id: Number,
  dateDebut?: String,
  dateRetour?: String,
  statut?: StatutEmprunt,
  emprunteur?: SerializedUrlObject,
  exemplaire?: SerializedUrlObject,
  dateEcheance?: Date
}

export enum StatutEmprunt {
  EN_COURS = "EN_COURS",
  EN_RETARD = "EN_RETARD",
  TERMINE = "TERMINE"
}
