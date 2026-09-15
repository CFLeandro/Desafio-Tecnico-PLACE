import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cidade } from '@domain/cidade';
import { Observable, from } from 'rxjs';
import {environment} from "../app/environments/environment";

@Injectable()
export class ProjetoService {

  constructor(private http: HttpClient) {}

    //------------------------------------------------
    /** Recupera a lista de cidades */
    //------------------------------------------------
    pesquisarCidades(): Observable<Cidade[]> {
    }

    //------------------------------------------------
    /** Exclui a cidade informada */
    //------------------------------------------------
    excluir(cidade: Cidade): Observable<any> {
    }

    //------------------------------------------------
    /** Salva a cidade informada */
    //------------------------------------------------
    salvar(cidade: Cidade): Observable<any> {
    }

}
