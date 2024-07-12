export const API_ROOT = 'http://localhost:8082';

export const OAuthConfigGoogle = {
    clientId: '390468462355-605umotentpl4jqu1pqdh57f6351lsv6.apps.googleusercontent.com',
    redirectUri: 'http://localhost:3000/authenticate',
    authUri: 'https://accounts.google.com/o/oauth2/auth',
};

export const OAuthConfigFacebook = {
    clientId: '1454809475067563',
    redirectUri: 'http://localhost:3000/authenticate',
    authUri: 'https://www.facebook.com/v20.0/dialog/oauth',
};
