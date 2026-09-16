import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ImportsModule } from './imports';
import { Cidade } from '@domain/cidade';
import { ProjetoService } from '@service/projeto-service';
import { MessageService } from 'primeng/api';

//-------------------------------------------------------------------------------------
/** Tela para cadastro de cidades */
//-------------------------------------------------------------------------------------
@Component({
    selector: 'cadastrar-cidade',
    templateUrl: 'cadastrar-cidade.html',
    standalone: true,
    imports: [ImportsModule],
    providers: [ProjetoService]
})
export class CadastrarCidade {

    //-------------------------------------------------------
    // Parâmetro de entrada para o componente
    //-------------------------------------------------------
    @Input() public cidade: Cidade = new Cidade();

    //-------------------------------------------------------
    // Evento lançado ao fechar a janela
    //-------------------------------------------------------
    @Output('onClose') private eventoFechaJanela = new EventEmitter<boolean>();

    //--------------------------------------------------------------
    /** Construtor. O MessageService vem do componente pai, então o
     *  toast aparece na tela de listagem depois que a janela fecha */
    //--------------------------------------------------------------
    constructor(private service: ProjetoService, private messageService: MessageService) {}

    //-------------------------------------------------------------------------------------
    /** Método chamado ao clicar no botao 'salvar' */
    //-------------------------------------------------------------------------------------
    public salvar(): void {
        // O service decide sozinho se é POST ou PUT, olhando se a cidade tem id
        this.service.salvar(this.cidade).subscribe({
            next: () => {
                this.messageService.add({ severity: 'success', summary: 'Info', detail: `Cidade '${this.cidade.nome}' salva com sucesso!` });
                // Avisa o pai que salvou, para ele recarregar a tabela
                this.eventoFechaJanela.emit(true);
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Não foi possível salvar a cidade.' });
            }
        });
    }

    //-------------------------------------------------------------------------------------
    /** Método chamado ao clicar no botao 'cancelar' */
    //-------------------------------------------------------------------------------------
    public cancelar(): void {
        this.eventoFechaJanela.emit(false) ;
    }

}
