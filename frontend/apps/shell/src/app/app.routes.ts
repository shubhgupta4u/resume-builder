import { NxWelcome } from './nx-welcome';
import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: 'subscriptionApp',
    loadChildren: () =>
      import('subscriptionApp/Routes').then((m) => m!.remoteRoutes),
  },
  {
    path: 'resumeApp',
    loadChildren: () => import('resumeApp/Routes').then((m) => m!.remoteRoutes),
  },
  {
    path: 'authApp',
    loadChildren: () => import('authApp/Routes').then((m) => m!.remoteRoutes),
  },
  {
    path: 'adminApp',
    loadChildren: () => import('adminApp/Routes').then((m) => m!.remoteRoutes),
  },
  {
    path: '',
    component: NxWelcome,
  },
];
