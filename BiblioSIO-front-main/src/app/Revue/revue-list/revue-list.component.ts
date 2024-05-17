import {Component} from '@angular/core';
import {Revue} from "../Revue";
import {RevueService} from "../revue.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-revue-list',
  templateUrl: './revue-list.component.html',
  styleUrls: ['./revue-list.component.css']
})
export class RevueListComponent {
  revues!: Revue[];
  selectedRevue!: Revue;

  constructor(
      private revueService: RevueService,
      private router: Router
  ) {
    this.getAllRevues();
  }

  public getAllRevues(){
    this.revueService.getAllRevues().subscribe((value) => {
      this.revues = value;
    });
  }

  public getRevueById(id: number) {
    this.revueService.getRevueById(id).subscribe((value) => {
      this.selectedRevue = value;
    });
  }

  public updateRevue(revue: Revue) {
    this.revueService.updateRevue(revue).subscribe();

  }

  public deleteRevue(revue: Revue) {
    this.revueService.deleteRevue(revue).subscribe();
  }

  public createRevue(revue: Revue) {
    this.revueService.createRevue(revue).subscribe();
  }

  openRevueDetails(revue: Revue) {
    this.router.navigate(['/revues/'+revue.id],
        {state: {revue: revue, solo: true}})
  }

  openCreateRevue() {
    this.router.navigate(['/revues/edit'],
        {state: {creating: true}})
  }
}
