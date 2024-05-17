import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Revue} from "../Revue";
import {RevueService} from "../revue.service";

@Component({
  selector: 'app-revue-editor',
  templateUrl: './revue-editor.component.html',
  styleUrls: ['./revue-editor.component.css']
})
export class RevueEditorComponent implements OnInit{
  revue: Revue = {} as Revue
  updating: boolean = false
  creating: boolean = false

  constructor(
      private revueService: RevueService,
      private router: Router
  ) {}

  ngOnInit() {
    if (history.state.revue!=null){
      this.revue=history.state.revue
    }
    this.creating = history.state.creating
    this.updating = history.state.updating
  }

  edit() {
    if(this.updating) {
      this.revueService.updateRevue(this.revue)
          .subscribe(()=>this.processUpdate())
    } else if (this.creating) {
      this.revueService.createRevue(this.revue)
          .subscribe((location)=>this.processCreate(location.toString()))
    }
  }

  processUpdate() {
    this.router.navigate(["/revues/"+this.revue.id],
      {state: {revue: this.revue}})
  }

  processCreate(url: string) {
    console.log(url)
    this.router.navigate([url])
  }
}
