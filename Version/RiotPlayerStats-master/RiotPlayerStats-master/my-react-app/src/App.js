    import logo from './logo.svg';
    import './App.css';
    import LoginForm from './Components/LoginForm/LoginForm';
    import RegisterForm from './Components/RegisterForm/RegisterForm';
    import HistoryForm from './Components/HistoryForm/HistoryForm';
    import MlForm from './Components/MlForm/MlForm';
    import Home from './Pages/Home'
    import AccountPage from './Pages/AccountPage'
    import {BrowserRouter, Routes, Route} from 'react-router-dom';
    import ProtectedRoute from './Components/ProtectedRoute';
    import BalanceForm from './Components/BalanceForm/BalanceForm';

    function App() {
      return (
        <div>
          <BrowserRouter>
            <Routes>
              <Route path="/home" element={<Home/>}/>
              <Route path="/login" element={<LoginForm/>}/>
              <Route path="/register" element={<RegisterForm/>}/>
              <Route
                    path="/account"
                    element={
                        <ProtectedRoute>
                            <AccountPage />
                        </ProtectedRoute>
                    }
                />
              <Route
                    path="/MachineLearning"
                    element={
                        <ProtectedRoute>
                            <MlForm />
                        </ProtectedRoute>
                    }
                />
              <Route
                path="/history"
                element={
                  <ProtectedRoute>
                    <HistoryForm />
                  </ProtectedRoute>
                }
              />
                            <Route
                path="/balance"
                element={
                  <ProtectedRoute>
                    <BalanceForm />
                  </ProtectedRoute>
                }
              />
            </Routes>
            
          </BrowserRouter>
        </div>
      );
    }

    export default App;
