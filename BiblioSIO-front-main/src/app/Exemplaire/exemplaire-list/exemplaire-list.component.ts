import { Component } from '@angular/core';
import {Exemplaire} from "../Exemplaire";
import {ExemplaireService} from "../exemplaire.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-exemplaire-list',
  templateUrl: './exemplaire-list.component.html',
  styleUrls: ['./exemplaire-list.component.css']
})
export class ExemplaireListComponent {
  exemplaires!: Exemplaire[];
  selectedExemplaire!: Exemplaire;

  constructor(
      public exemplaireService: ExemplaireService,
      private router: Router
  ) {
    this.getAllExemplaires();
  }

  public getAllExemplaires(){
    this.exemplaireService.getAllExemplaires().subscribe((value) => {
      this.exemplaires = value;
    });
  }

  public getExemplaireById(id: number) {
    this.exemplaireService.getExemplaireById(id).subscribe((value) => {
      this.selectedExemplaire = value;
    })
  }

  public updateExemplaire(exemplaire: Exemplaire) {
    this.exemplaireService.updateExemplaire(exemplaire).subscribe();

  }

  public deleteExemplaire(exemplaire: Exemplaire) {
    this.exemplaireService.deleteExemplaire(exemplaire).subscribe();
  }

  public createExemplaire(exemplaire: Exemplaire) {
    this.exemplaireService.createExemplaire(exemplaire).subscribe();
  }

  openExemplaireDetails(exemplaire: Exemplaire) {
    this.router.navigate(['/exemplaires/'+exemplaire.id],
        {state: {exemplaire: exemplaire, solo: true}})
  }

  openCreateExemplaire() {
    this.router.navigate(['/exemplaires/edit'],
        {state: {creating: true}})
  }

}
