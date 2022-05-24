import { parse } from "cookie";

export async function handle({ event, resolve }) {
    const cookies = parse(event.request.headers.get('cookie') || '');

    const response = await resolve(event);
    
    if(cookies['token'] === undefined) {
        if(event.url.pathname === '/register') return response;
        if(event.url.pathname === '/login') return response;

        return Response.redirect(getNewUrl(event.url.origin, 'login'), 303);
    }

    const res = await fetch(`http://localhost:8080/api/v1/users/token/${cookies['token']}`);
    if(!res.ok) {
        if(event.url.pathname === '/register') return response;
        if(event.url.pathname === '/login') return response;

        return Response.redirect(getNewUrl(event.url.origin, 'login'), 303);
    }
    
    if (event.url.pathname == '/login' || event.url.pathname == '/register') {
        if(cookies['token'] !== undefined) {
            return Response.redirect(getNewUrl(event.url.origin, ''), 303);
        }
    }

    return response;
}

export async function getSession(event) {
    const cookies = parse(event.request.headers.get('cookie') || '');
    return { token: cookies['token'] };
}

function getNewUrl(origin, pathname) {
    return `${origin}${origin[origin.length - 1] == '/' ? '' : '/'}${pathname}`;
}