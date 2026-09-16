import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cidade } from '@domain/cidade';
import { Observable } from 'rxjs';
import {environment} from "../app/environments/environment";

@Injectable()
export class ProjetoService {

  // Montada uma vez só, a partir do environment
  private readonly urlCidades = `${environment.apiUrl}${environment.urlCidades}`;

  constructor(private http: HttpClient) {}

    //------------------------------------------------
    /** Recupera a lista de cidades */
    //------------------------------------------------
    pesquisarCidades(): Observable<Cidade[]> {
        return this.http.get<Cidade[]>(this.urlCidades);
    }

    //------------------------------------------------
    /** Exclui a cidade informada */
    //------------------------------------------------
    excluir(cidade: Cidade): Observable<any> {
        return this.http.delete<void>(`${this.urlCidades}/${cidade.id}`);
    }

    //------------------------------------------------
    /** Salva a cidade informada.
     *  Se já tem id é alteração (PUT), senão é cadastro novo (POST) */
    //------------------------------------------------
    salvar(cidade: Cidade): Observable<any> {
        if (cidade.id) {
            return this.http.put<void>(this.urlCidades, cidade);
        }

        return this.http.post<void>(this.urlCidades, cidade);
    }

}
