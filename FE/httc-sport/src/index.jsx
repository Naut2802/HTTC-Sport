import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { createHashRouter,RouterProvider } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import App from './App';
import GlobalStyles from './components/GlobalStyles';
import reportWebVitals from './reportWebVitals';

const router = createHashRouter([
    {
      path: "/*",
      element: <App />,
    }
  ]);
  
  ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
      <RouterProvider router={router}>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <GlobalStyles>
                <App />
            </GlobalStyles>
            <ToastContainer position="bottom-right" theme="colored" />
        </LocalizationProvider>
      </RouterProvider>
    </React.StrictMode>
  );
reportWebVitals();
