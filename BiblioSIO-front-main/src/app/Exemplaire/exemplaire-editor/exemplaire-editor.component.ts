import {Component, OnInit} from '@angular/core';
import {Exemplaire} from "../Exemplaire";
import {ExemplaireService} from "../exemplaire.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {Revue} from "../../Revue/Revue";
import {RevueService} from "../../Revue/revue.service";

@Component({
  selector: 'app-exemplaire-editor',
  templateUrl: './exemplaire-editor.component.html',
  styleUrls: ['./exemplaire-editor.component.css']
})
export class ExemplaireEditorComponent implements OnInit {
  exemplaire: Exemplaire = {} as Exemplaire
  updating: boolean = false
  creating: boolean = false
  revues$!: Observable<Revue[]>

  constructor(
      private exemplaireService: ExemplaireService,
      private router: Router
  ) {}

  ngOnInit() {
    if (history.state.revue!=null){
      this.exemplaire=history.state.revue
    }
    this.creating = history.state.creating
    this.updating = history.state.updating
  }

  edit() {
    if(this.updating) {
      this.exemplaireService.updateExemplaire(this.exemplaire)
          .subscribe(()=>this.processUpdate())
    } else if (this.creating) {
      this.exemplaireService.createExemplaire(this.exemplaire)
          .subscribe((location)=>this.processCreate(location.toString()))
    }
  }

  processUpdate() {
    this.router.navigate(["/exemplaires/"+this.exemplaire.id],
      {state: {exemplaire: this.exemplaire}})
  }

  processCreate(url: string) {
    console.log(url)
    this.router.navigate([url])
  }
}
