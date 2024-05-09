import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;

public class Paciente {
    private JTextField idText;
    private JTextField nombreText;
    private JTextField apellidoText;
    private JTextField fechaText;
    private JTextField horaText;
    private JTextField telefonoText;
    private JButton GUARDARButton;
    private JButton CANCELARButton;
    private JButton ACTUALIZARButton;
    private JButton BUSCARButton;
    private JTable table1;
    private JTextField buscarText;
    private JScrollPane tabla_1;
    private Container Main;

    PreparedStatement pst;
    Connection con;

    //public static void main(String[] args){
    // JFrame frame = new JFrame("PACIENTE");
    //frame.setContentPane(new Paciente().Main);
    //frame.pack();
    //frame.setVisible(true);
    // }

    public Paciente() {
        connect();
        tabla_load();
        BUSCARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String buscar = buscarText.getText();

                    pst = con.prepareStatement("select nombre,apellido,fecha,hora,telefono from paciente where id = ?");
                    pst.setString(1, buscar);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String nombre = rs.getString(1);
                        String apellido = rs.getString(2);
                        String fecha = rs.getString(3);
                        String hora = rs.getString(4);
                        String telefono = rs.getString(5);

                        nombreText.setText(nombre);
                        apellidoText.setText(apellido);
                        fechaText.setText(fecha);
                        horaText.setText(hora);
                        telefonoText.setText(telefono);

                    } else {
                        nombreText.setText("");
                        apellidoText.setText("");
                        fechaText.setText("");
                        horaText.setText("");
                        telefonoText.setText("");
                        JOptionPane.showMessageDialog(null, "No tiene cita agendada");

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        ACTUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, nombre, apellido, fecha, hora, telefono;
                id = idText.getText();
                nombre = nombreText.getText();
                apellido = apellidoText.getText();
                fecha = fechaText.getText();
                hora = horaText.getText();
                telefono = telefonoText.getText();
                try {
                    pst = con.prepareStatement("update employee set nombre = ?,apellido = ?, fecha = ?, hora =?, telefono = ? where id = ?");
                    pst.setString(1, nombre);
                    pst.setString(2, apellido);
                    pst.setString(3, fecha);
                    pst.setString(4, hora);
                    pst.setString(5, telefono);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro actualizado");
                    tabla_load();
                    nombreText.setText("");
                    apellidoText.setText("");
                    fechaText.setText("");
                    horaText.setText("");
                    telefonoText.setText("");
                    idText.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        CANCELARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                id = idText.getText();

                try {
                    pst = con.prepareStatement("delete from paciente  where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Su cita a sido cancelada");
                    tabla_load();
                    nombreText.setText("");
                    apellidoText.setText("");
                    fechaText.setText("");
                    horaText.setText("");
                    telefonoText.setText("");
                    idText.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });
        GUARDARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, nombre, apellido, fecha, hora, telefono;
                id = idText.getText();
                nombre = nombreText.getText();
                apellido = apellidoText.getText();
                fecha = fechaText.getText();
                hora = horaText.getText();
                telefono = telefonoText.getText();

                try {
                    pst = con.prepareStatement("insert into(id,nombre,apellido,fecha,hara,telefono)values (?,?,?,?,?,?)");
                    pst.setString(1, id);
                    pst.setString(2, nombre);
                    pst.setString(3, apellido);
                    pst.setString(4, fecha);
                    pst.setString(5, hora);
                    pst.setString(6, telefono);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "¡¡¡¡Registro agregado!!!!!");
                    //tabla_carga();
                    idText.setText("");
                    nombreText.setText("");
                    apellidoText.setText("");
                    fechaText.setText("");
                    horaText.setText("");
                    telefonoText.setText("");
                    idText.requestFocus();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/paciente", "root", "");
            System.out.println("Successs");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();

        }
    }

    void tabla_load() {
        try {
            pst = con.prepareStatement("select * from paciente");
            ResultSet rs = pst.executeQuery();
            Object DbUtils;
           // table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

