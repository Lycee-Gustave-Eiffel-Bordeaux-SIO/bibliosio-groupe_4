export interface Emprunteur {
  id: Number
  nomEmprunteur?: string
  prenomEmprunteur?: string
  mailEmprunteur?: string
  promo?: string
  classe?: Classe
}
export enum Classe {
  SIO1A = "SIO1A",
  SIO1B = "SIO1B",
  SIO2A = "SIO2A",
  SIO2B = "SIO2B",
}

export class VotreComponente {
  ClasseArray = Object.values(Classe);

  constructor() { }
}
