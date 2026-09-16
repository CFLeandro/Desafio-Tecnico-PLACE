
//---------------------------------------------------
/** Classe que guarda os dados de uma cidade */
//---------------------------------------------------

export class Cidade {
    // Campos opcionais porque numa cidade nova ainda não temos nada preenchido
    id?: number;
    nome?: string;
    uf?: string;
    capital?: boolean;
}
