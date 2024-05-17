import {Revue} from "../Revue/Revue";

export interface Exemplaire {
  id: Number,
  titre?: String,
  anneeParution?: String,
  moisParution?: String,
  statut?: String,
  revue?: Revue
}
