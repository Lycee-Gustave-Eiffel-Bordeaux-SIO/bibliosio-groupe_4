import { Component } from '@angular/core';
import {Emprunt} from "../Emprunt";
import {EmpruntService} from "../emprunt.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-emprunt-list',
  templateUrl: './emprunt-list.component.html',
  styleUrls: ['./emprunt-list.component.css']
})
export class EmpruntListComponent {
  emprunts!: Emprunt[];
  selectedEmprunt!: Emprunt;

  constructor(
      private empruntService: EmpruntService,
      private router: Router
  ) {
    this.getAllEmprunts();
  }

  public getAllEmprunts(){
    this.empruntService.getAllEmprunts().subscribe((value) => {
      this.emprunts = value;
    });
  }

  public updateEmprunt(emprunt: Emprunt) {
    this.empruntService.updateEmprunt(emprunt).subscribe();

  }

  public deleteEmprunt(emprunt: Emprunt) {
    this.empruntService.deleteEmprunt(emprunt).subscribe();
  }

  public createEmprunt(emprunt: Emprunt) {
    this.empruntService.createEmprunt(emprunt).subscribe();
  }

  public getEmpruntById(id: number){
    this.empruntService.getEmpruntById(id).subscribe((value) => {
      this.selectedEmprunt = value;
    })
  }

  openEmpruntDetails(emprunt: Emprunt) {
    this.router.navigate(['/emprunts/'+emprunt.id],
        {state: {emprunt: emprunt, solo: true}})
  }

  openCreateEmprunt() {
    this.router.navigate(['/emprunts/edit'],
        {state: {creating: true}})
  }
}
