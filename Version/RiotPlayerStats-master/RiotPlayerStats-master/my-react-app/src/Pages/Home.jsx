import Header from '../Components/Header'

import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Home.css'; // stilurile pot fi modificate după preferințe

const Home = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const [textValue, setTextValue] = useState('');

  return (
    <div className="wrapper">
      <div className="header">
        <div className="navbar">
          <div className="menu-icon" onClick={() => setMenuOpen(!menuOpen)}>
            &#9776;
          </div>
        </div>
        {menuOpen && (
          <div className="menu-dropdown">
            <Link to="/home" onClick={() => setMenuOpen(false)}>Home</Link>
            <Link to="/register" onClick={() => setMenuOpen(false)}>Register</Link>
            <Link to="/login" onClick={() => setMenuOpen(false)}>Login</Link>
            <Link to="/machinelearning" onClick={() => setMenuOpen(false)}>MachineLearning</Link>
            <Link to="/history" onClick={() => setMenuOpen(false)}>History</Link>
            <Link to="/balance" onClick={() => setMenuOpen(false)}>Balancing ARAM</Link>
          </div>
        )}
      </div>

          <h1>Home</h1>

          <div className="explanation-box">
              <p>
 Bine ai venit în aplicația <strong>Sistem de Predicție a Șansei de Câștig în League of Legends</strong>!<br />
  Această platformă este concepută pentru a te ajuta să înțelegi mai bine cât de echilibrat este un meci înainte de a începe. Prin introducerea punctajelor jucătorilor, sistemul folosește algoritmi inteligenți pentru a calcula șansele fiecărei echipe de a câștiga.<br />
  <br />
  Fiecare pagină din aplicație include o descriere clară, menită să te ghideze în utilizarea funcționalităților. Indiferent dacă ești la prima vizită sau un utilizator experimentat, navigarea este simplă și intuitivă.<br />
  <br />
  În meniul principal poți accesa rapid paginile de autentificare, înregistrare, istoric de predicții, balansare ARAM sau zona dedicată algoritmului de machine learning.<br />
  <br />
  Scopul aplicației este să ofere o experiență utilă și educativă, combinând pasiunea pentru gaming cu analiza datelor. Începe explorarea și descoperă cum poți folosi datele pentru a înțelege mai bine jocul!

              </p>
          </div>
    </div>
  );
};

export default Home;
