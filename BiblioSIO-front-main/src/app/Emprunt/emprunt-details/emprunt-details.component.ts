import {Component, Input, OnInit} from '@angular/core';
import {Emprunt, StatutEmprunt} from "../Emprunt";
import {EmpruntService} from "../emprunt.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-emprunt-details',
  templateUrl: './emprunt-details.component.html',
  styleUrls: ['./emprunt-details.component.css']
})
export class EmpruntDetailsComponent implements OnInit{
  @Input() emprunt!: Emprunt
  @Input() solo: boolean = true

  constructor(
      private empruntService: EmpruntService,
      private router: Router,
      private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(){
    if(this.emprunt==null) {
      this.emprunt=history.state.emprunt
      if (history.state.emprunt==null) {
        this.empruntService.getEmpruntById(Number(this.activatedRoute.snapshot.paramMap.get("id")))
            .subscribe(emprunt=>this.emprunt=emprunt)
      }
    }
    this.solo=history.state.solo
  }

  delete(){
    this.empruntService.deleteEmprunt(this.emprunt)
        .subscribe(()=>this.router.navigate(['/emprunts']))
  }

  update() {
    this.router.navigate(["/emprunts/edit"],
        {state: {emprunt: this.emprunt,
            updating: true}})
  }

  termine() {
    return this.emprunt.statut==StatutEmprunt.TERMINE
  }
}
