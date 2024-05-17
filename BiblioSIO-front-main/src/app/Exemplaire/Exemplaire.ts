import {SerializedUrlObject} from "../utils/SerializedUrlObject";

export interface Exemplaire {
  id: Number,
  titre?: String,
  dateParution?: String,
  statut?: String,
  revue?: SerializedUrlObject
}
