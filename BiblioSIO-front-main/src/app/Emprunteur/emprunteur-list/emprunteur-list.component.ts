import {Component} from '@angular/core';
import {EmprunteurService} from "../emprunteur.service";
import {Classe, Emprunteur} from "../Emprunteur";
import {Router} from "@angular/router";

@Component({
  selector: 'app-emprunteur-list',
  templateUrl: './emprunteur-list.component.html',
  styleUrls: ['./emprunteur-list.component.css']
})
export class EmprunteurListComponent {
  emprunteurs!: Emprunteur[];
  selectedEmprunteur!: Emprunteur;
  ClasseArray!: Classe[];
  classeSelectionnee!: Classe;

  constructor(
    public emprunteurService: EmprunteurService,
    private router: Router
  ) {
    this.ClasseArray = Object.values(Classe);
    this.classeSelectionnee = this.ClasseArray[0];
    this.getAllEmprunteurs();
  }

  public getAllEmprunteurs(){
    this.emprunteurService.getAllEmprunteurs().subscribe((value) => {
      this.emprunteurs = value;
    });
  }

  public getEmprunteurById(id: number) {
    this.emprunteurService.getEmprunteurById(id).subscribe((value) => {
      this.selectedEmprunteur = value;
    })
  }

  public updateEmprunteur(emprunteur: Emprunteur) {
    this.emprunteurService.updateEmprunteur(emprunteur).subscribe();

  }

  public deleteEmprunteur(emprunteur: Emprunteur) {
    this.emprunteurService.deleteEmprunteur(emprunteur).subscribe();
  }

  public createEmprunteur(emprunteur: Emprunteur) {
    this.emprunteurService.createEmprunteur(emprunteur).subscribe();
  }

  openEmprunteurDetails(emprunteur: Emprunteur) {
    this.router.navigate(['/emprunteurs/'+emprunteur.id],
        {state: {emprunteur: emprunteur, solo: true}})
  }

  openCreateEmprunteur() {
    this.router.navigate(['/emprunteurs/edit'],
        {state: {creating: true}})
  }


}
