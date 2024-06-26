import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Exemplaire} from "../Exemplaire";
import {ExemplaireService} from "../exemplaire.service";

@Component({
  selector: 'app-exemplaire-details',
  templateUrl: './exemplaire-details.component.html',
  styleUrls: ['./exemplaire-details.component.css']
})
export class ExemplaireDetailsComponent implements OnInit {
  @Input() exemplaire!: Exemplaire
  @Input() solo: boolean = true

  constructor(
      private exemplaireService: ExemplaireService,
      private router: Router,
      private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(){
    if(this.exemplaire==null) {
      this.exemplaire=history.state.exemplaire
      if (history.state.exemplaire==null) {
        this.exemplaireService.getExemplaireById(Number(this.activatedRoute.snapshot.paramMap.get("id")))
            .subscribe(exemplaire=>this.exemplaire=exemplaire)
      }
    }
    this.solo=history.state.solo
  }

  delete(){
    this.exemplaireService.deleteExemplaire(this.exemplaire)
        .subscribe(()=>this.router.navigate(['/exemplaires']))
  }

  update() {
    this.router.navigate(["/exemplaires/edit"],
        {state: {exemplaire: this.exemplaire,
            updating: true}})
  }

  statut() {
    if(!this.exemplaire) {
      return "Indisponible"
    }
    return "Disponible"
  }

  gotoRevue(id: Number | undefined) {
    if(id){
      this.router.navigate(["/revues/" + id]);
    }
  }
}
