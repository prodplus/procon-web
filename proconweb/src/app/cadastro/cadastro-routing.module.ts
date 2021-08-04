import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadConsumidorResolver } from '../resources/cad-consumidor.resolver';
import { CadFornecedorResolver } from '../resources/cad-fornecedor.resolver';
import { CadUsuarioResolver } from '../resources/cad-usuario.resolver';
import { ListaConsumidoresResolver } from '../resources/lista-consumidores.resolver';
import { ListaFornecedoresResolver } from '../resources/lista-fornecedores.resolver';
import { ListaUsuariosResolver } from '../resources/lista-usuarios.resolver';
import { CadConsumidorComponent } from './consumidores/cad-consumidor/cad-consumidor.component';
import { ListaConsumidoresComponent } from './consumidores/lista-consumidores/lista-consumidores.component';
import { CadFornecedoresComponent } from './fornecedores/cad-fornecedores/cad-fornecedores.component';
import { ListaFornecedoresComponent } from './fornecedores/lista-fornecedores/lista-fornecedores.component';
import { CadUsuarioComponent } from './usuarios/cad-usuario/cad-usuario.component';
import { ListaUsuariosComponent } from './usuarios/lista-usuarios/lista-usuarios.component';

export const routes: Routes = [
  {
    path: 'consumidores',
    children: [
      {
        path: '',
        component: ListaConsumidoresComponent,
        resolve: { page: ListaConsumidoresResolver },
      },
      {
        path: 'novo',
        children: [
          { path: '', component: CadConsumidorComponent },
          {
            path: ':id',
            component: CadConsumidorComponent,
            resolve: { consumidor: CadConsumidorResolver },
          },
        ],
      },
    ],
  },
  {
    path: 'fornecedores',
    children: [
      {
        path: '',
        component: ListaFornecedoresComponent,
        resolve: { page: ListaFornecedoresResolver },
      },
      {
        path: 'novo',
        children: [
          { path: '', component: CadFornecedoresComponent },
          {
            path: ':id',
            component: CadFornecedoresComponent,
            resolve: { fornecedor: CadFornecedorResolver },
          },
        ],
      },
    ],
  },
  {
    path: 'usuarios',
    children: [
      {
        path: '',
        component: ListaUsuariosComponent,
        resolve: { page: ListaUsuariosResolver },
      },
      {
        path: 'novo',
        children: [
          { path: '', component: CadUsuarioComponent },
          {
            path: ':id',
            component: CadUsuarioComponent,
            resolve: { usuario: CadUsuarioResolver },
          },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CadastroRoutingModule {}
