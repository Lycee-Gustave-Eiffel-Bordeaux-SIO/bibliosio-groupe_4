import {Injectable} from '@angular/core';
import {GenericService} from "../utils/generic.service";
import {Revue} from "./Revue";

@Injectable({
  providedIn: 'root'
})
export class RevueService extends GenericService<Revue> {
  protected override className = "Revue"
  protected override url = "http://localhost:8080/revues"

  getAllRevues() {
    return this.http.get<Revue[]>(this.url);
  }

  getRevueById(id: Number) {
    return this.http.get<Revue>(this.url + "/" + id);
  }

  updateRevue(revue: Revue) {
    return this.http.put(this.url + "/" + revue.id, revue);
  }

  deleteRevue(revue: Revue) {
    return this.http.delete(this.url + "/" + revue.id);
  }

  createRevue(revue: Revue) {
    const requestBody = {
      id: 0,
      titre: revue.titre
    };
    return this.http.post(this.url, requestBody);
  }
}
