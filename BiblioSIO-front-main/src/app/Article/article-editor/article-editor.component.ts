import {Component, OnInit} from '@angular/core';
import {Article} from "../Article";
import {ArticleService} from "../article.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {Exemplaire} from "../../Exemplaire/Exemplaire";
import {ExemplaireService} from "../../Exemplaire/exemplaire.service";

@Component({
  selector: 'app-article-editor',
  templateUrl: './article-editor.component.html',
  styleUrls: ['./article-editor.component.css']
})
export class ArticleEditorComponent implements OnInit{
  article: Article = {} as Article
  updating: boolean = false
  creating: boolean = false

  constructor(
      private articleService: ArticleService,
      private router: Router
  ) {}

  ngOnInit() {
    if (history.state.article!=null){
      this.article=history.state.article
    }
    this.creating = history.state.creating
    this.updating = history.state.updating
  }

  edit() {
    if(this.updating) {
      this.articleService.updateArticle(this.article)
          .subscribe(()=>this.processUpdate())
    } else if (this.creating) {
      this.articleService.createArticle(this.article)
          .subscribe((location)=>this.processCreate(location.toString()))
    }
  }

  processUpdate() {
    this.router.navigate(["/articles/"+this.article.id],
      {state: {article: this.article}})
  }

  processCreate(url: string) {
    console.log(url)
    this.router.navigate([url])
  }
}
