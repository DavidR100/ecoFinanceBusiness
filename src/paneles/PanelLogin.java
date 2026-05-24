/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package paneles;
 
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.SwingConstants;
import javax.swing.text.*;
import ui.UITheme;
import ui.RoundedBorder;
 

public class PanelLogin extends javax.swing.JPanel {
 
    // === COMPONENTES ===
    private PlaceholderField txtUsuario;
    private PlaceholderPasswordField txtContrasena;
    private JLabel lblError;
 
    // === LISTENER para navegar al dashboard después del login ===
    public interface LoginListener {
        void onLoginSuccess();
        void onRegisterClick();
    }
 
    private LoginListener loginListener;
 
    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }
 
    public PanelLogin() {
        initComponents();
        setLayout(new BorderLayout());
        setBackground(UITheme.BG);
        build();
    }
 
    private void build() {
        add(buildContainer(), BorderLayout.CENTER);
    }
 
    // ============================================================
    // CONTENEDOR PRINCIPAL
    // ============================================================
    private JPanel buildContainer() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(new Color(0x0D3D21));
 
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 28, 28));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(860, 520));
 
        card.add(buildLeft(),  BorderLayout.WEST);
        card.add(buildRight(), BorderLayout.CENTER);
 
        outer.add(card);
        return outer;
    }
 
    // ============================================================
    // LADO IZQUIERDO — BLANCO CON LOGO
    // ============================================================
    private JPanel buildLeft() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(340, 0));
        panel.setBorder(new EmptyBorder(40, 32, 32, 32));
 
        // === LOGO ===
        JPanel logoArea = new JPanel();
        logoArea.setLayout(new BoxLayout(logoArea, BoxLayout.Y_AXIS));
        logoArea.setOpaque(false);
 
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(CENTER_ALIGNMENT);
        try {
            java.net.URL url = getClass().getResource("/resources/LOGO.jpeg");
            if (url != null) {
                Image img = ImageIO.read(url).getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(img));
            } else {
                lblLogo.setText("🌿");
                lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
            }
        } catch (Exception e) {
            lblLogo.setText("🌿");
            lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        }
 
        JLabel lblTagline = new JLabel(
            "<html><center>La plataforma inteligente para<br>" +
            "gestionar tu pizzería de forma<br>sostenible y responsable.</center></html>"
        );
        lblTagline.setFont(UITheme.FONT_SMALL);
        lblTagline.setForeground(UITheme.MUTED);
        lblTagline.setAlignmentX(CENTER_ALIGNMENT);
        lblTagline.setHorizontalAlignment(SwingConstants.CENTER);
        lblTagline.setBorder(new EmptyBorder(16, 8, 0, 8));
 
        logoArea.add(Box.createVerticalGlue());
        logoArea.add(lblLogo);
        logoArea.add(lblTagline);
        logoArea.add(Box.createVerticalGlue());
 
        // === FOOTER ===
        JLabel lblFooter = new JLabel("© 2026 EcoFinance Business · Todos los derechos reservados.");
        lblFooter.setFont(UITheme.FONT_TINY);
        lblFooter.setForeground(new Color(0xBBBBBB));
        lblFooter.setAlignmentX(CENTER_ALIGNMENT);
 
        panel.add(logoArea,  BorderLayout.CENTER);
        panel.add(lblFooter, BorderLayout.SOUTH);
 
        return panel;
    }
 
    // ============================================================
    // LADO DERECHO — VERDE CON FORMULARIO
    // ============================================================
    private JPanel buildRight() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, UITheme.VERDE_PRINCIPAL,
                    0, getHeight(), UITheme.VERDE_MEDIO
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, 30, getHeight());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 28, 28));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
 
        // ── Wrapper que ocupa todo el espacio y usa GridBagLayout para centrar verticalmente ──
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(40, 50, 20, 50)); // top, left, bottom, right
 
        // ── Contenido del formulario con GridBagLayout fila por fila ──
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
 
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.WEST;
 
        // === TÍTULO ===
        JLabel lblTitulo = new JLabel("Iniciar Sesión");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        c.gridy = 0; c.insets = new Insets(0, 0, 4, 0);
        form.add(lblTitulo, c);
 
        // === SUBTÍTULO ===
        JLabel lblSub = new JLabel("Accede a tu cuenta para continuar");
        lblSub.setFont(UITheme.FONT_SMALL);
        lblSub.setForeground(new Color(255, 255, 255, 180));
        c.gridy = 1; c.insets = new Insets(0, 0, 28, 0);
        form.add(lblSub, c);
 
        // === LABEL USUARIO ===
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(UITheme.FONT_BOLD);
        lblUsuario.setForeground(new Color(255, 255, 255, 220));
        c.gridy = 2; c.insets = new Insets(0, 0, 6, 0);
        form.add(lblUsuario, c);
 
        // === CAMPO USUARIO ===
        txtUsuario = new PlaceholderField("Ingresa tu usuario");
        styleField(txtUsuario);
        txtUsuario.setMaximumSize(null); // quitar límite para que GridBag lo maneje
        c.gridy = 3; c.insets = new Insets(0, 0, 16, 0);
        form.add(txtUsuario, c);
 
        // === LABEL CONTRASEÑA ===
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(UITheme.FONT_BOLD);
        lblContrasena.setForeground(new Color(255, 255, 255, 220));
        c.gridy = 4; c.insets = new Insets(0, 0, 6, 0);
        form.add(lblContrasena, c);
 
        // === CAMPO CONTRASEÑA ===
        txtContrasena = new PlaceholderPasswordField("Ingresa tu contraseña");
        styleField(txtContrasena);
        txtContrasena.setMaximumSize(null);
        c.gridy = 5; c.insets = new Insets(0, 0, 4, 0);
        form.add(txtContrasena, c);
 
        // === LABEL ERROR ===
        lblError = new JLabel(" ");
        lblError.setFont(UITheme.FONT_SMALL);
        lblError.setForeground(UITheme.AMBER);
        c.gridy = 6; c.insets = new Insets(0, 0, 18, 0);
        form.add(lblError, c);
 
        // === BOTÓN ===
        JButton btnLogin = createLoginButton();
        btnLogin.setMaximumSize(null);
        c.gridy = 7; c.insets = new Insets(0, 0, 14, 0);
        form.add(btnLogin, c);
 
        // === LINK REGISTRO ===
        JPanel registerRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        registerRow.setOpaque(false);
 
        JLabel lblNoTienes = new JLabel("¿No tienes cuenta?");
        lblNoTienes.setFont(UITheme.FONT_SMALL);
        lblNoTienes.setForeground(new Color(255, 255, 255, 160));
 
        JLabel lblRegistrate = new JLabel("Regístrate aquí");
        lblRegistrate.setFont(UITheme.FONT_BOLD);
        lblRegistrate.setForeground(Color.WHITE);
        lblRegistrate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblRegistrate.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (loginListener != null) loginListener.onRegisterClick();
            }
            @Override public void mouseEntered(MouseEvent e) { lblRegistrate.setForeground(UITheme.MENTA); }
            @Override public void mouseExited(MouseEvent e)  { lblRegistrate.setForeground(Color.WHITE); }
        });
        registerRow.add(lblNoTienes);
        registerRow.add(lblRegistrate);
        c.gridy = 8; c.insets = new Insets(0, 0, 0, 0);
        form.add(registerRow, c);
 
        // Agregar form al wrapper centrado verticalmente
        GridBagConstraints wc = new GridBagConstraints();
        wc.gridx = 0; wc.gridy = 0;
        wc.fill = GridBagConstraints.HORIZONTAL;
        wc.weightx = 1.0;
        wc.weighty = 1.0;
        wc.anchor = GridBagConstraints.CENTER;
        wrapper.add(form, wc);
 
        // === FOOTER — separado en SOUTH para no quedar flotando ===
        JLabel lblFooterRight = new JLabel("🌱 Finanzas responsables, futuro sostenible.");
        lblFooterRight.setFont(UITheme.FONT_TINY);
        lblFooterRight.setForeground(new Color(255, 255, 255, 100));
        lblFooterRight.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.setBorder(new EmptyBorder(0, 0, 16, 0));
        footerPanel.add(lblFooterRight, BorderLayout.CENTER);
 
        panel.add(wrapper, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);
        return panel;
    }
 
    // ============================================================
    // ESTILO COMÚN PARA CAMPOS (transparente, letra blanca)
    // ============================================================
    private void styleField(JComponent field) {
        field.setFont(UITheme.FONT_BODY);
        field.setForeground(Color.WHITE);
        if (field instanceof JTextField tf) {
            tf.setCaretColor(Color.WHITE);
        }
        // CLAVE: sin fondo opaco — se ve el verde del panel
        field.setOpaque(false);
        field.setBackground(new Color(0, 0, 0, 0));
 
        // Usamos tu RoundedBorder + padding interno
        field.setBorder(new CompoundBorder(
            new RoundedBorder(14, new Color(255, 255, 255, 90)),
            new EmptyBorder(10, 14, 10, 14)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        field.setAlignmentX(LEFT_ALIGNMENT);
    }
 
    // ============================================================
    // BOTÓN INICIAR SESIÓN
    // ============================================================
    private JButton createLoginButton() {
        JButton btn = new JButton("→   Iniciar sesión") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 14, 14));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(UITheme.AMBER);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(14, 24, 14, 24));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(0xD4941A)); }
            @Override public void mouseExited(MouseEvent e)  { btn.setBackground(UITheme.AMBER); }
        });
        btn.addActionListener(e -> validarLogin());
        return btn;
    }
 
    // ============================================================
    // CAMPO DE TEXTO CON PLACEHOLDER
    // ============================================================
    static class PlaceholderField extends JTextField {
        private final String placeholder;
 
        PlaceholderField(String placeholder) {
            this.placeholder = placeholder;
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            // Pintamos fondo transparente redondeado
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 28));
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 14, 14));
            g2.dispose();
            super.paintComponent(g);
 
            // Pintamos placeholder si está vacío y sin foco
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g3 = (Graphics2D) g.create();
                g3.setFont(getFont());
                g3.setColor(new Color(255, 255, 255, 110));
                Insets ins = getInsets();
                FontMetrics fm = g3.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g3.drawString(placeholder, ins.left, y);
                g3.dispose();
            }
        }
    }
 
    // ============================================================
    // CAMPO DE CONTRASEÑA CON PLACEHOLDER
    // ============================================================
    static class PlaceholderPasswordField extends JPasswordField {
        private final String placeholder;
 
        PlaceholderPasswordField(String placeholder) {
            this.placeholder = placeholder;
            setEchoChar('●');
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 28));
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 14, 14));
            g2.dispose();
            super.paintComponent(g);
 
            if (getPassword().length == 0 && !isFocusOwner()) {
                Graphics2D g3 = (Graphics2D) g.create();
                g3.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                g3.setColor(new Color(255, 255, 255, 110));
                Insets ins = getInsets();
                FontMetrics fm = g3.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g3.drawString(placeholder, ins.left, y);
                g3.dispose();
            }
        }
    }
 
    // ============================================================
    // LÓGICA — VALIDAR LOGIN
    // ============================================================
    private void validarLogin() {
        String usuario    = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
 
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            lblError.setText("⚠ Completa todos los campos.");
            return;
        }
 
        if (usuario.equals("admin") && contrasena.equals("1234")) {
            lblError.setText(" ");
            if (loginListener != null) loginListener.onLoginSuccess();
        } else {
            lblError.setText("⚠ Usuario o contraseña incorrectos.");
            txtContrasena.setText("");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
